package com.dawid.cli.states;

import com.dawid.States;
import com.dawid.cli.Commands;
import com.dawid.IClient;

public class LobbyStateCli extends StateCli {
    public LobbyStateCli(IClient cli) {
        super(cli);
        name = States.LOBBY;

        commands.put(Commands.start, this::start);
        commands.put(Commands.leave, this::leave);
    }

    public void start(String[] args) {
        client.getServerCommunicator().startGame();
    }

    public void leave(String[] args) {
        client.getServerCommunicator().leaveLobby();
    }
}
