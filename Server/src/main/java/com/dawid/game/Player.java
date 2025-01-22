package com.dawid.game;

public class Player {
    int id;
    int homeField;
    int winField;

    public Player(int id, int winField) {
        this.id = id;
        homeField = id;
        this.winField = winField;
    }
}
