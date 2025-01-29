package com.dawid;

public interface CommandHandler {
    /**
     * Executes the command and returns response.
     * @param command
     * @return respons to the client
     */
    public void exec(String command);

    static CommandHandler create(Player player) {
        return new CommandExecutor(player);
    }
}
