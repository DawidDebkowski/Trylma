package com.dawid.cli.states;

import com.dawid.States;
import com.dawid.cli.Commands;
import com.dawid.cli.ICLIClient;

public class DisconnectedStateCli extends StateCli {
    public DisconnectedStateCli(ICLIClient cli) {
        super(cli);
        name = States.DISCONNECTED;

        commands.put(Commands.exit, this::exit);
    }

    private void exit(String[] args) {
        client.exit();
        client.getServerCommunicator().disconnect();
    }
}
