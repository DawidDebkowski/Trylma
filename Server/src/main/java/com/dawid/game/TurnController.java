package com.dawid.game;

import java.util.List;
/**
 * Keeps track of whose turn it is.
 */
public class TurnController {
    private List<Player> players;
    private int currentPlayer;
    public TurnController(List<Player> players) {
        this.players = players;
        currentPlayer = 0;
    }
    public TurnController(List<Player> players, int startingPlayer) {
        this.players = players;
        this.currentPlayer = startingPlayer;
    }
    /**
     * Returns the player whose turn it is.
     * @return The player whose turn it is.
     */
    public Player getCurrrentPlayer() {
        return players.get(currentPlayer);
    }
    /**
     * Advances the turn to the next player.
     */
    public void nextTurn() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }
}
