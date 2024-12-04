package com.dawid;

import java.util.EnumMap;
import java.util.Scanner;

public class CLI
{
    ClientState clientState;
    Scanner scanner;

    public CLI() {
        scanner = new Scanner(System.in);
        clientState = new PlayingState();
    }

    public void mainLoop() {
        println("Welcome to the Trylma game!");
        println("Type \"help\" for a list of commands or just connect and start playing!");
        while(true) {
            println("Input a command");
            String[] args = parseInput(scanner.nextLine());
            try {
                clientState.executeCommand(args);
            } catch (CommandException e) {
                println(e.getMessage());
                continue;
            }
        }
    }

    public void ChangeState(ClientState newState) {
        clientState = newState;
    }

    public String[] parseInput(String in) {
        if(in.isEmpty()) {
            return new String[0];
        }
        return in.split(" ");
    }

    public void println(String message) {
        System.out.println(message);
    }
}
