package com.dawid.game;


import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Represents a player in the game.
 */
public class Player {
    @Setter
    @Getter
    private Lobby lobby;
    @Getter
    private Integer number;
    protected final  PrintWriter out;
    /**
     * Creates a new player.
     * @param out The output stream to send messages to the player.
     */
    public Player(OutputStream out) {
        this.out = new PrintWriter(out, true);
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void makeTurn() {
        sendMessage("TURN");
    }
}
