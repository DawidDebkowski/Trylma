package com.dawid.server;

import java.net.Socket;

public class CommandExecutor implements joinGameHandler {
    private Socket socket;
    public CommandExecutor(Socket socket) {
        this.socket = socket;
    }
    @Override
    public String exec(String command) {
        return null;
    }
    public Boolean joinedGame() {
        return false;
    }
}
