package com.dawid.cli.states;

import com.dawid.cli.Commands;
import com.dawid.IClient;

public class DisconnectedState extends State {
    public DisconnectedState(IClient cli) {
        super(cli);
        name = States.DISCONNECTED;

        commands.put(Commands.exit, this::exit);
    }

    private void exit(String[] args) {
        client.exit();
        client.getServerCommunicator().disconnect();
    }
}
