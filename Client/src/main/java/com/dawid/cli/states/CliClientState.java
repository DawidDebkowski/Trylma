package com.dawid.cli.states;

import com.dawid.States;

/**
 * Interface for all States.
 */
public interface CliClientState {
    public void executeCommand(String[] args) throws CommandException;
    public States getState();
}
