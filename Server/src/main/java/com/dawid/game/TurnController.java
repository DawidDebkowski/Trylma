package com.dawid.game;

import com.dawid.server.Player;

import java.util.List;

public class TurnController {
    private List<Player> players;
    private int currentPlayer;
    public TurnController(List<Player> players) {
        this.players = players;
        currentPlayer = 0;
    }
    public Player getCurrrentPlayer() {
        return players.get(currentPlayer);
    }
    public void nextTurn() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }
}
