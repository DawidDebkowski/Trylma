package com.dawid.game;

public class Field {
    /**
     * Number of the player that owns this home .
     * -1 if the field is not home region.
     */
    private int home;
    public Field() {
        home = -1;
    }
    public void setHome(int home) {
        this.home = home;
    }
    public int getHome() {
        return home;
    }
}
