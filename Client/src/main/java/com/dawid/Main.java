package com.dawid;

import java.io.IOException;

public class Main {
    public static void main( String[] args )
    {
        CLI cli = null;
        try {
            cli = new CLI(new ServerCommunicator("localhost", 5005));
            cli.mainLoop();
        } catch (IOException e) {
            System.err.println("Could not connect to server!");
        }
    }
}
