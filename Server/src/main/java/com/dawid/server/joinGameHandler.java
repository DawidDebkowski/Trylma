package com.dawid.server;

import java.net.Socket;

public interface joinGameHandler {
    /**
     * Executes the command and returns response.
     * @param command
     * @return respons to the client
     */
    public String exec(String command);

    /**
     * Checks if the player joined a game.
     * @return true if player is already in game
     */
    public Boolean joinedGame();

    static joinGameHandler create(Socket socket) {
        return new CommandExecutor(socket);
    }
}
