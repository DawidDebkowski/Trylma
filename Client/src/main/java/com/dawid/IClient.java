package com.dawid;

import com.dawid.game.Board;
import com.dawid.states.ClientState;

public interface IClient {
    void moveOnBoard(int player, String x, String y);

    Board getBoard();

    ServerCommunicator getSocket();

    void changeState(ClientState newState);

    void exit();

    void message(String message);

    //TODO refactor ServerCommunicator to remove this method
    void prompt();
}
