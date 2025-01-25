package com.dawid.game;


import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Represents a player in the game.
 */
public class Player {
    private Lobby lobby;
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
    public Integer getNumber() {
        return number;
    }
    public void sendMessage(String message) {
        out.println(message);
    }
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
    public Lobby getLobby() {
        return lobby;
    }
    public void makeTurn() {
        sendMessage("TURN");
    }
}
