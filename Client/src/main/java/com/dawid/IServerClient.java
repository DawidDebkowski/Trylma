package com.dawid;

import com.dawid.game.Board;
import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;

import java.util.Collection;

/**
 * Specifies commands that the server sends to the player.
 */
public interface IServerClient {

    /**
     * Starts the game. It should only be called by the server.
     * Sets up all required classes and GUI to play a game.
     * @param myID this clients id
     * @param board board to play
     * @param variant variant
     * @param playerCount this game's player count
     */
    void startGame(int myID, Board board, Variant variant, int playerCount);

    /**
     * Makes a move on the internal board.
     * Should be called by the server.
     * Doesn't perform any checks except that the fields must exist.
     * -1 -1 -1 -1 means skip turn
     * @param player moving player
     * @param x from (in format x_y)
     * @param y to (in format x_y)
     */
    void moveOnBoard(int player, String x, String y);

    /**
     * Changes the state of the client.
     * In case of GUI it changes the scene.
     * @param newState new state
     */
    void changeState(States newState);

    /**
     * Exits the app
     */
    void exit();

    /**
     * Displays a message
     * @param message msg to be displayed
     */
    void message(String message);

    /**
     * Updates the list of lobbies
     * @param lobbies new list
     */
    void updateLobbies(Collection<LobbyInfo> lobbies);

    /**
     * Sends a signal that it's this Client's turn to play
     */
    void setMyTurn();
}
