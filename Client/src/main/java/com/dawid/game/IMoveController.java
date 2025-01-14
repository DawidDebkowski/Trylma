package com.dawid.game;

import java.util.Collection;
import java.util.List;

public interface IMoveController {
    public List<Integer> setupPlayers();
    public void setupPawns(List<Integer> players);

    /**
     * Recursively searches for all possible moves from a given field. Includes all jumps.
     * @param startField
     * @return
     */
    public Collection<Field> getPossibleMoves(Field startField);

    /**
     * Sets given pawn in given coordinates.
     */
    public void movePawn(int pawn, int sx, int sy, int fx, int fy);
}
