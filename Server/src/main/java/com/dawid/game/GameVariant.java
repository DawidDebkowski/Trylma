package com.dawid.game;

import com.dawid.server.Lobby;

public interface GameVariant {
    public void initializeGame(Lobby lobby);
}