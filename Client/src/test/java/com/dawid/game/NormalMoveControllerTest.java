package com.dawid.game;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class NormalMoveControllerTest extends TestCase {
    Board board;
    NormalMoveController controller;

    @Override
    protected void setUp() throws Exception {
        board = new DavidStarBoard();
        controller = new NormalMoveController(board, 6);
        super.setUp();
    }

    public void testGetPossibleMoves() {
        controller.setupPawns();
        Field f = board.getField(6, 10);
        f.setPawn(7);
        Field[] f1 = board.getNeighboringFields(f).toArray(new Field[0]);
        Field[] f2 = controller.getPossibleMoves(f).toArray(new Field[0]);
        assertArrayEquals(f1, f2);

    }
}