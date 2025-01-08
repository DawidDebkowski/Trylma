package com.dawid;

import com.dawid.states.MenuState;

import java.io.IOException;

public class Main {
    public static void main( String[] args )
    {
        CLI cli;
        try {
            cli = new CLI(new ServerCommunicator("localhost", 5005));
            cli.changeState(new MenuState(cli));
            cli.mainLoop();
        } catch (IOException e) {
            System.err.println("Could not connect to server!");
        }
    }
}
