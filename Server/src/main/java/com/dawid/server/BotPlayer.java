package com.dawid.server;

import java.io.OutputStream;

public class BotPlayer extends Player{
    /**
     * Creates a new player.
     *
     * @param out The output stream to send messages to the player.
     */
    public BotPlayer(OutputStream out) {
        super(out);
    }
}
