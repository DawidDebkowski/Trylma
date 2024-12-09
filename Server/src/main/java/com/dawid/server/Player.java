package com.dawid.server;


import java.io.OutputStream;
import java.io.PrintWriter;

public class Player {
    private Lobby lobby;
    private int number;
    private PrintWriter out;
    public Player(OutputStream out) {
        this.out = new PrintWriter(out, true);
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
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
}
