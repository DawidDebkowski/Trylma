package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class will control how the game is played.
 * It combines a board and a variant to make a game.
 */
public class GameController {
    // ID of player that is using this controller
    int playerID;
    // List of currently playing playerIDs
    Collection<Integer> players;
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

    public Collection<Coordinates> getPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = moveController.getPossibleMoves(field);
        Collection<Coordinates> possibleMovesList = new ArrayList<>();
        return null;
    }

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
