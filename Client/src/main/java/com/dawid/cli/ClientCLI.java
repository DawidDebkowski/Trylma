package com.dawid.cli;

import com.dawid.IServerClient;
import com.dawid.ServerCommunicator;
import com.dawid.States;
import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.cli.states.CliClientState;
import com.dawid.cli.states.CommandException;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ClientCLI implements IServerClient, ICLIClient {
    private CliClientState cliClientState;
    private final ServerCommunicator socket;
    private final Scanner scanner;
    private final Board board;

    boolean isRunning = true;

    public ClientCLI(ServerCommunicator server) {
        scanner = new Scanner(System.in);
        this.socket = server;
        socket.setClient(this);

        board = new DavidStarBoard();
    }

    @Override
    public void mainLoop() {
        println("Welcome to the Trylma game!");
        println("Type \"help\" for a list of commands or just connect and start playing!");
        while (isRunning) {
            prompt();
            final String[] args = parseInput(scanner.nextLine());
            try {
                cliClientState.executeCommand(args);
            } catch (CommandException e) {
                println(e.getMessage());
                continue;
            }
        }
    }

    /**
     * Used to reset the prompt after server message.
     */
    @Override
    public void prompt() {
        System.out.printf("[%s]>", cliClientState.getState());
    }

    @Override
    public void updateLobbies(Collection<LobbyInfo> lobbies) {
        //TODO implement
        return;
    }

    @Override
    public void myTurn() {

    }

    @Override
    public void setMyTurn() {

    }

    @Override
    public Collection<LobbyInfo> getLobbies() {
        //TODO implement
        return List.of();
    }

    /**
     * The board should do it but right now it cant.
     */
    @Override
    public void moveOnBoard(int player, String x, String y) {
        println("<debug> Player " + player + " moved on " + x + " " + y);
        return;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public ServerCommunicator getServerCommunicator() {
        return socket;
    }

    @Override
    public void exit() {
        isRunning = false;
    }

    @Override
    public void startGame(int myID, Board board, Variant variant, int playerCount) {

    }

    @Override
    public void message(String message) {
        println(message);
    }

    @Override
    public void changeState(States state) {
        // it doesnt work anyway after the refactor but
        // to make it work we would need to create a new CliClientState
        // here based on state variable
    }

    @Override
    public String[] parseInput(String in) {
        if (in.isEmpty()) {
            return new String[0];
        }
        return in.split(" ");
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

}
