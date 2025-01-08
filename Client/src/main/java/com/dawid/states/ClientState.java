package com.dawid.states;

/**
 * Interface for all States.
 */
public interface ClientState {
    public void executeCommand(String[] args) throws CommandException;
    public States getName();
}
