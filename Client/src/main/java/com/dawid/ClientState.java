package com.dawid;

public interface ClientState {
    public void executeCommand(String[] args) throws CommandException;
}
