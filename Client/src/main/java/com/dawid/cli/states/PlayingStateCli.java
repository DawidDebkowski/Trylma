package com.dawid.cli.states;

import com.dawid.States;
import com.dawid.cli.Commands;
import com.dawid.IClient;

public class PlayingStateCli extends StateCli {

    public PlayingStateCli(IClient cli) {
        super(cli);
        name = States.PLAYING;

        commands.put(Commands.move, this::move);
        commands.put(Commands.showBoard, this::showBoard);
        commands.put(Commands.disconnect, this::disconnect);
    }

    private void move(String[] args) {
//        boolean status = client.getSocket().move(args[1], args[2]);
    }

    private void showBoard(String[] args) {
        System.out.println("--- Current Board State ---");
        client.getBoard().printBoard();
    }


    private void disconnect(String[] args) {
        client.getServerCommunicator().leaveLobby();
    }
}
