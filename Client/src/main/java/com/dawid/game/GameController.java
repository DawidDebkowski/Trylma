package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class will control how the game is played.
 * It combines a board and a variant to make a game.
 */
public class GameController {
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

    GameController(Board board, IMoveController moveController, int playerNumber) {
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
    public void makeMove(int row, int col, int player) {
        moveController.movePawn(row, col, player);

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
     * @param row
     * @param col
     * @param destRow
     * @param destCol
     * @return
     */
    public boolean tryMove(int row, int col, int destRow, int destCol) {
        if(!isYourTurn()) return false;
        Field startField = board.getField(row, col);
        Field finishField = board.getField(destRow, destCol);
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
        return null;
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
        GameController gameController = new GameController(board, new NormalMoveController(board, 6), 0);
        gameController.startGame();
        board.debugPrint();
        board.getField(6, 10).setPawn(7);
        board.getField(6, 12).setPawn(7);
        board.getField(6, 16).setPawn(7);
        board.getField(7, 9).setPawn(7);
        board.getField(9, 7).setPawn(7);
        board.printBoard();
        gameController.showPossibleMoves(new Coordinates(6, 10));
        board.printBoard();
    }
}
