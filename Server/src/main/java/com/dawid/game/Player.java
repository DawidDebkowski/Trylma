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

    public int getId() {return id;}
    public int getWinField() {return winField;}
    public int getHomeField() {return homeField;}
}
