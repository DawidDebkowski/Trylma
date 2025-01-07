package com.dawid.states;

import com.dawid.Commands;
import com.dawid.IClient;

public class DisconnectedState extends State {
    public DisconnectedState(IClient cli) {
        super(cli);
        name = "MENU";

        commands.put(Commands.join, this::join);
        commands.put(Commands.create, this::create);
        commands.put(Commands.exit, this::exit);
    }

    private void join(String[] args) {
        client.getSocket().join(Integer.parseInt(args[1]));
//        client.changeState(new PlayingState(client));
    }

    private void create(String[] args) {
        client.getSocket().create();
//        client.changeState(new PlayingState(client));
    }

    private void exit(String[] args) {
        client.exit();
        client.getSocket().disconnect();
    }
}
