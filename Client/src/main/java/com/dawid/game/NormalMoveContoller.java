package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;

public class NormalMoveContoller implements IMoveController {
    Board board;

    @Override
    public Collection<Field> getPossibleMoves(Field startField) {
        Collection<Field> possibleMoves = new ArrayList<>();
        Collection<Field> neighboringFields = board.getNeighboringFields(startField);
        for (Field field : neighboringFields) {
            if(field.getPawn() != -1) {
                possibleMoves.add(field);
            }
        }
        return possibleMoves;
    }
}
