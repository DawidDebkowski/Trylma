package com.dawid.states;

public interface ClientState {
    public void executeCommand(String[] args) throws CommandException;
}
