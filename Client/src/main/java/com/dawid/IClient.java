package com.dawid;

import com.dawid.game.Board;
import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.states.ClientState;

import java.util.Collection;

public interface IClient {
    void moveOnBoard(int player, String x, String y);

    Board getBoard();

    ServerCommunicator getSocket();

    void changeState(ClientState newState);

    void exit();

    public void startGame(int myID, Board board, Variant variant, int playerCount);

    void message(String message);

    //TODO refactor ServerCommunicator to remove this method
    void prompt();

    void updateLobbies(Collection<LobbyInfo> lobbies);

    public void myTurn();

    Collection<LobbyInfo> getLobbies();
}
