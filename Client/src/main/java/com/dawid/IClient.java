package com.dawid;

import com.dawid.game.Board;
import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.cli.states.ClientState;

import java.util.Collection;

public interface IClient {

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
     * @param newState
     */
    void changeState(ClientState newState);

    void exit();

    public void startGame(int myID, Board board, Variant variant, int playerCount);

    void message(String message);

    //TODO refactor ServerCommunicator to remove this method
    void prompt();

    void updateLobbies(Collection<LobbyInfo> lobbies);

    void myTurn();

    Collection<LobbyInfo> getLobbies();
    Board getBoard();
    ServerCommunicator getServerCommunicator();
}
