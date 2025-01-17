package com.dawid.cli.states;

import com.dawid.States;
import com.dawid.cli.Commands;
import com.dawid.cli.ICLIClient;

public class MenuStateCli extends StateCli {
    public MenuStateCli(ICLIClient cli) {
        super(cli);
        name = States.MENU;

        commands.put(Commands.join, this::join);
        commands.put(Commands.create, this::create);
        commands.put(Commands.exit, this::exit);
    }

    private void join(String[] args) {
        client.getServerCommunicator().join(Integer.parseInt(args[1]));
//        client.changeState(new PlayingState(client));
    }

    private void create(String[] args) {
        client.getServerCommunicator().create();
//        client.changeState(new PlayingState(client));
    }

    private void exit(String[] args) {
        client.exit();
        client.getServerCommunicator().disconnect();
    }
}
