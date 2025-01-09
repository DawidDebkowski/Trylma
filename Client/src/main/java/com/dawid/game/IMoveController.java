package com.dawid.game;

import java.util.Collection;

public interface IMoveController {
    public void setupPawns();
    public Collection<Field> getPossibleMoves(Field field);
}
