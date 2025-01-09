package com.dawid.game;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class DavidStarBoardTest extends TestCase {
    DavidStarBoard davidStarBoard;

    @Override
    protected void setUp() throws Exception {
        davidStarBoard = new DavidStarBoard();
        super.setUp();
    }

    public void testGetField() {
        int row = 4;
        int col = 6;
        Field f = davidStarBoard.getField(row, col);
        assertNotNull(f);
        assertEquals(row, davidStarBoard.getCoordinatesMap().get(f).getRow());
        assertEquals(col, davidStarBoard.getCoordinatesMap().get(f).getColumn());

        row = 0;
        col = 12;
        f = davidStarBoard.getField(row, col);
        assertNotNull(f);
        assertEquals(row, davidStarBoard.getCoordinatesMap().get(f).getRow());
        assertEquals(col, davidStarBoard.getCoordinatesMap().get(f).getColumn());

    }
}