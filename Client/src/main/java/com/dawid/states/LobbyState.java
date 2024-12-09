package com.dawid.states;

import com.dawid.CLI;
import com.dawid.Commands;

public class LobbyState extends State {
    public LobbyState(CLI cli) {
        super(cli);

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
