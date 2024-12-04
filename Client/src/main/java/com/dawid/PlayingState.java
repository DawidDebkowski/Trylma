package com.dawid;

public class PlayingState extends State{

    PlayingState(CLI cli) {
        super(cli);

        commands.put(Commands.move, this::move);
        commands.put(Commands.showBoard, this::showBoard);
        commands.put(Commands.disconnect, this::disconnect);
    }

    private void move(String[] args) {
        System.out.println("Move command" + args[1] + args[2]);
    }

    private void showBoard(String[] args) {
        System.out.println("Show command");
    }

    private void disconnect(String[] args) {
        System.out.println("Disconnecting");
        client.changeState(new DisconnectedState(client));
    }
}
