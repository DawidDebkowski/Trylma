package com.dawid;

import java.io.IOException;

/**
 * Client / Frontend to Server communication
 */
public interface IServerCommands {
    /**
     * Connect to the server
     * @param serverAdress adres
     * @param port port
     * @throws IOException when bad
     */
    void connect(String serverAdress, int port) throws IOException;

    /**
     * dc from server
     */
    void disconnect();

    /**
     * Sends a move request to the server. It should be previously verified.
     * @param sx from
     * @param sy from
     * @param fx to
     * @param fy to
     */
    void move(int sx, int sy, int fx, int fy);

    /**
     * Sends a join request.
     * @param lobbyID desired lobby
     */
    void join(int lobbyID);

    /**
     * Sends a leve request
     */
    void leaveLobby();

    /**
     * Sends a start game request.
     */
    void startGame();

    /**
     * Sends a create game request.
     */
    void create();

    /**
     * Sends a lobbyInfo  request.
     */
    void getLobbyInfo();

    /**
     * Sends current lobby's new variant
     */
    void setVariant(String variant);
}
