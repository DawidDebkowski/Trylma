package com.dawid;

import java.util.EnumMap;
import java.util.Scanner;

public class CLI
{
    EnumMap<Commands, ICommand> commands;
    Scanner scanner;

    public CLI() {
        scanner = new Scanner(System.in);
        commands = new EnumMap<>(Commands.class);

        commands.put(Commands.help, this::help);
        commands.put(Commands.move, this::move);
        commands.put(Commands.showBoard, this::showBoard);
    }

    public static void main( String[] args )
    {
        CLI cli = new CLI();
        cli.mainLoop();
    }

    public void mainLoop() {
        println("Welcome to the Trylma game!");
        println("Type \"help\" for a list of commands or just connect and start playing!");
        while(true) {
            println("Input a command");
            String[] args = takeInput();
            Commands command = Commands.stringToCommand(args[0]);
            if(command == null) {
                println("Unknown command");
                continue;
            }
            if(args.length-1 < command.minArgs()) {
                println("Not enough arguments");
                continue;
            }
            commands.get(command).execute(args);
        }
    }

    private void help(String[] args) {
        System.out.printf("%-7s %-15s %s\n", "Short", "Command", "Usage");
        for(Commands command : Commands.values()) {
            System.out.printf("%-7s %-15s - %s\n", command.getShortcut(), command.getFullName(), command.getDescription());
        }
    }

    private void move(String[] args) {

    }

    private void showBoard(String[] args) {

    }

    public String[] takeInput() {
        String in = scanner.nextLine();
        if(in.isEmpty()) {
            return new String[0];
        }
        return in.split(" ");
    }

    public void println(String message) {
        System.out.println(message);
    }
}
