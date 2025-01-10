package com.dawid.server;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Lobby {
    // We could use state pattern, but it's not necessary if only 2 states are possible
    private boolean inGame = false;
    private Board board;
    private final Collection<Player> players;
    public Lobby(List<Player> players) {
        this.players = players;
    }
    public Lobby() {
        this.players = new ArrayList<>();
        //only DavidStarBoard is implemented
        board = new DavidStarBoard();
    }
    public void notifyAll(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }
    public int getPlayerCount() {
        return players.size();
    }
    public void addPlayer(Player player) {
        players.add(player);
        player.setLobby(this);
    }
    public void removePlayer(Player player) {
        players.remove(player);
    }
    public void startGame() {
        if(board.correctPlayerCount(this.getPlayerCount())) {
            inGame = true;
            notifyAll("Started game");
            Collection<Integer> playerNumbers = board.getPlayerNumbers(this.getPlayerCount());
            Iterator<Integer> iterator = playerNumbers.iterator();
            for(Player player : players) {
                player.setNumber(iterator.next());
            }
        }
        else {
            notifyAll("ERROR: Incorrect number of players");
        }
    }
    public void endGame() {
        inGame = false;
    }

}
