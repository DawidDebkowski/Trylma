package com.dawid;

import com.dawid.cli.ClientCLI;
import com.dawid.cli.states.MenuState;
import junit.framework.TestCase;

import java.io.IOException;

public class ClientCLITest extends TestCase {
    ClientCLI clientCli = new ClientCLI(new ServerCommunicator("localhost", 5005));

    public ClientCLITest() throws IOException {
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testMainLoop() {

    }

    public void testParse() {
        clientCli.changeState(new MenuState(clientCli));
        String[] args = clientCli.parseInput("move 1 2 abc");
        assertEquals("move", args[0]);
        assertEquals("1", args[1]);
        assertEquals("2", args[2]);
        assertEquals("abc", args[3]);
    }
}