package com.dawid;

import com.dawid.game.Board;
import com.dawid.game.GameEngine;
import com.dawid.game.LobbyInfo;

import java.io.IOException;
import java.util.Collection;

/**
 * It should be used by frontend components.
 * So you do not call server-only methods.
 */
public interface IClient {
    /**
     * Get commands to send a command to the server.
     * It is here so we do not need to duplicate all commands
     * in the client.
     * Call client.getServerCommands().move() to move
     * @return commands obj
     */
    IServerCommands getServerCommands();
    void connect(String serverAdress, int port) throws IOException;
    Collection<LobbyInfo> getLobbies();
    Board getBoard();
    GameEngine getGameController();
}
