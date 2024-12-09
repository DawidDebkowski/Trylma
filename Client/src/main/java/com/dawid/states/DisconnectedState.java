package com.dawid.states;

import com.dawid.CLI;
import com.dawid.Commands;

public class DisconnectedState extends State {
    public DisconnectedState(CLI cli) {
        super(cli);

        commands.put(Commands.join, this::join);
        commands.put(Commands.create, this::create);
        commands.put(Commands.exit, this::exit);
    }

    private void join(String[] args) {
        client.getSocket().join(Integer.parseInt(args[1]));
        client.changeState(new PlayingState(client));
    }

    private void create(String[] args) {
        client.getSocket().create();
        client.changeState(new PlayingState(client));
    }

    private void exit(String[] args) {
        client.endLoop();
    }
}
