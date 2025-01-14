package com.dawid.game;

public class LobbyInfo {
    private int id;
    private int currentPlayers;
    private int maxPlayers;
    private Variant variant;

    public LobbyInfo(int id, int currentPlayers, Variant variant) {
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = 6;
        this.variant = variant;
    }

    public int getId() {
        return id;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Variant getVariant() {
        return variant;
    }
}
