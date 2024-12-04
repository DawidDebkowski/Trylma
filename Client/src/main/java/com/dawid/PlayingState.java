package com.dawid;

import java.io.Serializable;
import java.util.EnumMap;

public class PlayingState implements ClientState {
    EnumMap<Commands, ICommand> commands;

    PlayingState() {
        commands = new EnumMap<>(Commands.class);

        commands.put(Commands.help, this::help);
        commands.put(Commands.move, this::move);
        commands.put(Commands.showBoard, this::showBoard);
    }

    private void help(String[] args) {
        System.out.printf("%-7s %-15s %s\n", "Short", "Command", "Usage");
        for(Commands command : Commands.values()) {
            System.out.printf("%-7s %-15s - %s\n", command.getShortcut(), command.getFullName(), command.getDescription());
        }
    }

    private void move(String[] args) {
        System.out.println("Move command" + args[1] + args[2]);
    }

    private void showBoard(String[] args) {
        System.out.println("Show command");
    }

    @Override
    public void executeCommand(String[] args) throws CommandException {
        Commands command = Commands.stringToCommand(args[0]);
        if(command == null) {
            throw new CommandException("Unknown command");
        }
        if(args.length-1 < command.minArgs()) {
            throw new CommandException("Not enough arguments");
        }
        commands.get(command).execute(args);
    }
}
