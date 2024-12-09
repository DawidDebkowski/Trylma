package com.dawid.server;

import java.util.ArrayList;
import java.util.List;

public class GamesManager {
    private static volatile GamesManager instance;
    private final List<Lobby> lobbies;
    private GamesManager () {
        lobbies = new ArrayList<>();
    }
    public static GamesManager getInstance() {
        if (instance == null) {
            synchronized(GamesManager.class) {
                if(instance == null) {
                    instance = new GamesManager();
                }
            }
        }
        return instance;
    }
    public void addLobby(Lobby lobby) {
        lobbies.add(lobby);
    }
    public Lobby getLobby(int id) {
        return lobbies.get(id);
    }
    public void removeLobby(int id) {
        lobbies.remove(id);
    }
    public boolean lobbyExists(int id) {
        return lobbies.size() > id;
    }
    public int getLobbyCount() {
        return lobbies.size();
    }
    public int getLobbyId(Lobby lobby) {
        for (int i = 0; i < lobbies.size(); i++) {
            if (lobbies.get(i).equals(lobby)) {
                return i;
            }
        }
        return -1;
    }
}
