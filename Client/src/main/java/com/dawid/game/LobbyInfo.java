package com.dawid.game;

public class LobbyInfo {
    final private int id;
    final private int currentPlayers;
    final private int maxPlayers;
    final private Variant variant;

    public LobbyInfo(int id, int currentPlayers, Variant variant, int maxPlayers) {
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
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
