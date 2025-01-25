package com.dawid.game;

import java.io.OutputStream;

public class BotPlayer extends Player {
    /**
     * Creates a new player.
     *
     * @param out The output stream to send messages to the player.
     */
    public BotPlayer(OutputStream out) {
        super(out);
    }
    @Override
    public void makeTurn() {
        String[] args = {"MOVE", "-1_-1", "-1_-1"};
        getLobby().makeMove(this, args);
    }
}
