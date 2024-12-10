package com.dawid.game;

public class Field {
    /**
     * Number of the player that owns this home .
     * 0 if the field is not home region.
     */
    private int home;
    public Field() {
        home = 0;
    }
    public void setHome(int home) {
        this.home = home;
    }
    public int getHome() {
        return home;
    }
}
