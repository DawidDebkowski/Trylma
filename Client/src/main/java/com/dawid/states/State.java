package com.dawid.states;

import com.dawid.CLI;
import com.dawid.Commands;

import java.util.EnumMap;

/**
 * All States inherit from state. It implements basic commands like help.
 * It also handles executing the command.
 */
public class State implements ClientState {
    protected String name;
    EnumMap<Commands, ICommand> commands;
    CLI client;

    public State(CLI cli){
        client = cli;
        commands = new EnumMap<>(Commands.class);

        commands.put(Commands.help, this::help);
    }

    private void help(String[] args) {
        System.out.printf("%-7s %-15s %s\n", "Short", "Command", "Usage");
        for(Commands command : commands.keySet()) {
            System.out.printf("%-7s %-15s - %s\n", command.getShortcut(), command.getFullName(), command.getDescription());
        }
    }

    @Override
    public void executeCommand(String[] args) throws CommandException {
        if(args.length == 0){
            throw new CommandException("No command specified");
        }
        Commands command = Commands.stringToCommand(args[0]);
        if(command == null) {
            throw new CommandException("Unknown command");
        }
        if(args.length-1 < command.minArgs()) {
            throw new CommandException("Not enough arguments");
        }
        if(commands.get(command) != null) {
            commands.get(command).execute(args);
        } else
            throw new CommandException("Unknown command in this state");
    }

    public String getName() {
        return name;
    }
}
