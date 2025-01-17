package com.dawid.cli;

import com.dawid.ServerCommunicator;
import com.dawid.States;
import com.dawid.game.Board;
import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;

import java.util.Collection;

public interface ICLIClient {
    void mainLoop();

    void prompt();

    void updateLobbies(Collection<LobbyInfo> lobbies);

    void myTurn();

    Collection<LobbyInfo> getLobbies();

    void moveOnBoard(int player, String x, String y);

    Board getBoard();

    ServerCommunicator getServerCommunicator();

    void exit();

    void startGame(int myID, Board board, Variant variant, int playerCount);

    void message(String message);

    void changeState(States state);

    String[] parseInput(String in);

    void println(String message);
}
