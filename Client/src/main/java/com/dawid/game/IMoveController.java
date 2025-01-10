package com.dawid.game;

import java.util.Collection;

public interface IMoveController {
    public Collection<Integer> setupPlayers();
    public void setupPawns(Collection<Integer> players);
    public Collection<Field> getPossibleMoves(Field field);
}
