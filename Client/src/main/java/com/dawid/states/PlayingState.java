package com.dawid.states;

import com.dawid.CLI;
import com.dawid.Commands;

public class PlayingState extends State{

    public PlayingState(CLI cli) {
        super(cli);
        name = "GAME";

        commands.put(Commands.move, this::move);
        commands.put(Commands.showBoard, this::showBoard);
        commands.put(Commands.disconnect, this::disconnect);
    }

    private void move(String[] args) {
        boolean status = client.getSocket().move(args[1], args[2]);
    }

    private void showBoard(String[] args) {
        System.out.println("--- Current Board State ---");
        client.getBoard().printBoard();
    }


    private void disconnect(String[] args) {
        client.getSocket().leaveLobby();
    }
}
