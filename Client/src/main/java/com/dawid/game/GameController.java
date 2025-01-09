package com.dawid.game;

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

    public Collection<Coordinates> GetPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());

        return null;
    }

    public static void main(String[] args) {
        DavidStarBoard board = new DavidStarBoard();
        GameController gameController = new GameController(board, new NormalMoveContoller(board, 6));
        gameController.startGame();
    }
}
