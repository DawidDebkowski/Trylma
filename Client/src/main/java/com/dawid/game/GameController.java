package com.dawid.game;

import java.util.Collection;

public class GameController {
    Board board;


    public Collection<Coordinates> GetPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());

    }
}
