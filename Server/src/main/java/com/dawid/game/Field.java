package com.dawid.game;

/**
 * 0 is no pawn
 */
public class Field {
    /**
     * Number of the player that owns this home .
     * 0 if the field is not home region.
     */
    private int home;
    private int pawn;
    /**
     *  For simplification of algorithms that find all the possible moves
     */
    boolean visited;
    public Field() {
        home = 0;
        pawn = 0;
        visited = false;
    }
    public void setHome(int home) {
        this.home = home;
    }
    public int getHome() {
        return home;
    }
    public int getPawn() {
        return pawn;
    }
    public void setPawn(int pawn) {
        this.pawn = pawn;
    }
}
