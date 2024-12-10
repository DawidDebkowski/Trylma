package com.dawid;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.states.ClientState;
import com.dawid.states.CommandException;

import java.util.Scanner;

public class CLI {
    private ClientState clientState;
    private final ServerCommunicator socket;
    private final Scanner scanner;
    private final Board board;

    boolean isRunning = true;

    public CLI(ServerCommunicator server) {
        scanner = new Scanner(System.in);
        this.socket = server;
        socket.setClient(this);

        board = new DavidStarBoard();
    }

    public void mainLoop() {
        println("Welcome to the Trylma game!");
        println("Type \"help\" for a list of commands or just connect and start playing!");
        while (isRunning) {
            System.out.printf("[%s]>", clientState.getName());
            final String[] args = parseInput(scanner.nextLine());
            try {
                clientState.executeCommand(args);
            } catch (CommandException e) {
                println(e.getMessage());
                continue;
            }
//            println("end loop");
        }
    }

    public Board getBoard() {
        return board;
    }

    public ServerCommunicator getSocket() {
        return socket;
    }

    public void endLoop() {
        isRunning = false;
    }

    public void changeState(ClientState newState) {
        clientState = newState;
    }

    public String[] parseInput(String in) {
        if (in.isEmpty()) {
            return new String[0];
        }
        return in.split(" ");
    }

    public void println(String message) {
        System.out.println(message);
    }
}
