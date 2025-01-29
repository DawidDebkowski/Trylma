package com.dawid;
/**
 * Represents a command that can be executed by the server.
 */
public interface Command {
    /**
     * Executes the command.
     * @param args The arguments of the command.
     */
    public void exec(String[] args);
}
