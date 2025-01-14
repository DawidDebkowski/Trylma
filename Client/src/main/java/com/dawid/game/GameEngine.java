package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class will control how the game is played.
 * It combines a board and a variant to make a game.
 *
 * Notatka: Licze ze ta klasa wyjasnia sama siebie komentarzami
 * ale zalozenie bylo takie ze bedzie kontrolowac przebieg gry
 *
 * niestety przez moje niezrouzmienie DavidStarBoard wyszlo glupio z koordynatami ale wedlug mnie
 * mozemy z tym pracowac, bo wiekszosc kodu jest na Field, wiec nic nie szkodzi
 *
 * Jak sobie to odpalisz to zobaczysz rozne boardy wyprintowane, jak poczytasz kod to zobaczysz o co chodzi
 *
 * Ogolnie do zrobienia jest jakiegos rodzaju mainLoop() gry, zeby no po prostu sie toczyla xd
 * tryMove() i getPossibleMoves() trzeba podlaczyc do planszy, ktorej nie ma na razie
 * ale tez ja trzeba zmienic
 * no i wtedy te metody powinny operowac na tych samych rodzajach koordynatow
 *
 * Na serwerze sa chyba jakies zmiany ale juz nie pamietam, ale raczej nieznaczace
 *
 * Notatka: a no i jest w fieldzie .visited, ktore jest czyms w stylu tablicy visited[] w
 * cudownych algorytmach grafowych zeby sie nie zapetlic bo mozna duzo razy trafic
 * na to samo pole
 * uznalem ze jak bedzie w fieldzie to bedzie to duze uproszczenie, bo nie wiedzialem do konca gdzie
 * trzymac informacje w tym czy pole zostalo odwiedzone czy nie
 * i mysle ze to jest okej pozdrawiam cieplutko z rodzina i powodzenia jutro
 *
 * sorry za chaos i wgl ale jakos programowanie obiektowe mnie czasami przytłacza
 */
public class GameEngine {
    // ID of player that is using this controller
    int playerID;
    // List of currently playing playerIDs
    List<Integer> players;
    // Index of the player that is currently moving in the players list
    int movingPlayerIndex = 0;
    // Board type
    Board board;
    // Game Variant
    IMoveController moveController;

    public GameEngine(Board board, IMoveController moveController, int playerNumber) {
        this.board = board;
        this.moveController = moveController;
        this.playerID = playerNumber;
    }

    public void startGame() {
        players = moveController.setupPlayers();
        moveController.setupPawns(players);
        board.printBoard();
    }

    /**
     *  If player owning this GameController can move.
     */
    public boolean isYourTurn() {
        return players.get(movingPlayerIndex) == playerID;
    }

    /**
     * It will just (without checking anything) make that move.
     * Should only be used if the server tells you to move, not for local moves.
     * Increments movingPlayerIndex
     */
    public void makeMove(int player, int sx, int sy, int fx, int fy)  {
        moveController.movePawn(player, sx, sy, fx, fy);

        //possible synchronization error
        movingPlayerIndex++;
        if(movingPlayerIndex == players.size()) {
            movingPlayerIndex = 0;
        }
    }

    /**
     * Validate local moves before sending them to the server.
     * It should be called when a player selects a place to move their pawn to.
     * It shouldn't be used to move the pawn - only the server's response should do that.
     * @return
     */
    public boolean tryMove(Coordinates from, Coordinates to) {
        if(!isYourTurn()) return false;
        Field startField = board.getField(from.getRow(), from.getColumn());
        Field finishField = board.getField(to.getRow(), to.getColumn());
        return moveController.getPossibleMoves(startField).contains(finishField);
    }

    /**
     * To be finished and integrated with GUI using a good coordinate system.
     * Returns a collection of all the possible moves from a given field.
     * @param c
     * @return
     */
    public Collection<Coordinates> getPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = moveController.getPossibleMoves(field);
        Collection<Coordinates> possibleMovesList = new ArrayList<>();
        for(Field possibleMove : possibleMoves) {
            possibleMovesList.add(board.getCoordinates(possibleMove));
        }
        return possibleMovesList;
    }

    /**
     * For testing. Marks all possible moves with a 9-pawn.
     */
    public void showPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = moveController.getPossibleMoves(field);
        for (Field f : possibleMoves) {
            f.setPawn(9);
        }
    }

    // ludzki test getPossibleMoves()
    public static void main(String[] args) {
        DavidStarBoard board = new DavidStarBoard();
        GameEngine gameEngine = new GameEngine(board, new NormalMoveController(board, 6), 0);
        gameEngine.startGame();
        board.debugPrint();
        board.getField(6, 10).setPawn(7);
        board.getField(6, 12).setPawn(7);
        board.getField(6, 16).setPawn(7);
        board.getField(7, 9).setPawn(7);
        board.getField(9, 7).setPawn(7);
        board.printBoard();
        gameEngine.showPossibleMoves(new Coordinates(6, 10));
        board.printBoard();
    }

    public Board getBoard() {
        return board;
    }
}
