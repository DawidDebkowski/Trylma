package com.dawid.states;

import com.dawid.Commands;
import com.dawid.IClient;

public class LobbyState extends State {
    public LobbyState(IClient cli) {
        super(cli);
        name = "LOBBY";

        commands.put(Commands.start, this::start);
        commands.put(Commands.leave, this::leave);
    }

    public void start(String[] args) {
        client.getSocket().startGame();
    }

    public void leave(String[] args) {
        client.getSocket().leaveLobby();
    }
}
