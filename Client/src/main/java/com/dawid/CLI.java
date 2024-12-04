package com.dawid;

import com.dawid.states.ClientState;
import com.dawid.states.CommandException;

import java.util.Scanner;

public class CLI {
    ClientState clientState;
    private ITrylmaProtocol socket;
    Scanner scanner;

    boolean isRunning = true;

    public CLI(ITrylmaProtocol server) {
        scanner = new Scanner(System.in);
        this.socket = server;
    }

    public void mainLoop() {
        println("Welcome to the Trylma game!");
        println("Type \"help\" for a list of commands or just connect and start playing!");
        while (isRunning) {
            println("Input a command");
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
    public ITrylmaProtocol getSocket() {
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
