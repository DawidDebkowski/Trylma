package com.dawid;

public class DisconnectedState extends State {
    DisconnectedState(CLI cli) {
        super(cli);

        commands.put(Commands.connect, this::connect);
        commands.put(Commands.exit, this::exit);
    }

    private void connect(String[] args) {
        System.out.println("Connect command" + args[1]);
        client.changeState(new PlayingState(client));
    }

    private void exit(String[] args) {
        client.endLoop();
    }
}
