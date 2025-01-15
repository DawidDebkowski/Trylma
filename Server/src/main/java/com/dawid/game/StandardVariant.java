package com.dawid.game;

import com.dawid.server.Lobby;

public class StandardVariant implements GameVariant {
    public void initializeGame(Lobby lobby) {
        return;
    }
    public Variant getVariant() {
        return Variant.NORMAL;
    }
}