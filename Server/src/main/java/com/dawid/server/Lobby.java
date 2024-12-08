package com.dawid.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lobby {
    private Collection<Player> players;
    public Lobby(List<Player> players) {
        this.players = players;
    }
    public Lobby() {
        this.players = new ArrayList<>();
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
}
