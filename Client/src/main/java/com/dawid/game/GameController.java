package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class will control how the game is played.
 * It combines a board and a variant to make a game.
 */
public class GameController {
    Board board;
    IMoveController moveController;

    GameController(Board board, IMoveController moveController) {
        this.board = board;
        this.moveController = moveController;
    }

    public void startGame() {
        moveController.setupPawns();
        board.printBoard();
    }

    public Collection<Coordinates> getPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = moveController.getPossibleMoves(field);
        Collection<Coordinates> possibleMovesList = new ArrayList<Coordinates>();
        return null;
    }

    public void showPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = moveController.getPossibleMoves(field);
        for (Field f : possibleMoves) {
            f.setPawn(9);
        }
    }

    public static void main(String[] args) {
        DavidStarBoard board = new DavidStarBoard();
        GameController gameController = new GameController(board, new NormalMoveController(board, 6));
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
