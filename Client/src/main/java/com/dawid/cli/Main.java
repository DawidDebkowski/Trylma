package com.dawid.cli;

import com.dawid.ServerCommunicator;
import com.dawid.States;
import com.dawid.cli.states.MenuStateCli;

import java.io.IOException;

public class Main {
    public static void main( String[] args )
    {
        ClientCLI clientCli;
        try {
            clientCli = new ClientCLI(new ServerCommunicator("localhost", 5005));
            clientCli.changeState(States.MENU);
            clientCli.mainLoop();
        } catch (IOException e) {
            System.err.println("Could not connect to server!");
        }
    }
}
