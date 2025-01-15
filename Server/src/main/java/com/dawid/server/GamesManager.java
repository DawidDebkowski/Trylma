package com.dawid.server;

import java.util.ArrayList;
import java.util.List;
/**
 * Manages the lobbies.
 * Singleton.
 * @see Lobby
 */
public class GamesManager {
    private static volatile GamesManager instance;
    private final List<Lobby> lobbies;
    private GamesManager () {
        lobbies = new ArrayList<>();
    }
    /**
     * Returns the instance of the GamesManager.
     * @return The instance of the GamesManager.
     */
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
    /**
     * Adds a lobby to the list of lobbies.
     * @param lobby The lobby to add.
     */
    public void addLobby(Lobby lobby) {
        lobbies.add(lobby);
    }
    /**
     * Returns the lobby with the given id.
     * @param id The id of the lobby.
     * @return The lobby with the given id.
     */
    public Lobby getLobby(int id) {
        return lobbies.get(id);
    }
    /**
     * Removes the lobby with the given id.
     * @param id The id of the lobby to remove.
     */
    public void removeLobby(int id) {
        lobbies.remove(id);
    }
    /**
     * Returns whether a lobby with the given id exists.
     * @param id The id of the lobby.
     * @return Whether a lobby with the given id exists.
     */
    public boolean lobbyExists(int id) {
        return lobbies.size() > id;
    }
    /**
     * Returns the number of lobbies.
     * @return The number of lobbies.
     */
    public int getLobbyCount() {
        return lobbies.size();
    }
    /**
     * Returns the id of the given lobby.
     * @param lobby The lobby.
     * @return The id of the given lobby.
     */
    public int getLobbyId(Lobby lobby) {
        for (int i = 0; i < lobbies.size(); i++) {
            if (lobbies.get(i).equals(lobby)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Returns the list of lobbies.
     * @return The list of lobbies.
     */
    public List<Lobby> getLobbies() {return lobbies;}
}
