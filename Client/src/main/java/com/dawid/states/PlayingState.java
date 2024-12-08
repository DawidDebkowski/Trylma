package com.dawid.states;

import com.dawid.CLI;
import com.dawid.Commands;

public class PlayingState extends State{

    PlayingState(CLI cli) {
        super(cli);

        commands.put(Commands.move, this::move);
        commands.put(Commands.showBoard, this::showBoard);
        commands.put(Commands.disconnect, this::disconnect);
    }

    private void move(String[] args) {
        boolean status = client.getSocket().move(args[1], args[2]);
        if (status) {
            System.out.println("Move ok");
        } else {
            System.out.println("Invalid move, try again.");
        }
    }

    private void showBoard(String[] args) {
        System.out.println("Show command");
    }

    private void disconnect(String[] args) {
        System.out.println("Disconnecting");
        client.changeState(new DisconnectedState(client));
    }
}
