package com.dawid;

import com.dawid.states.DisconnectedState;
import com.dawid.states.PlayingState;
import junit.framework.TestCase;

import java.io.IOException;

public class CLITest extends TestCase {
    CLI cli = new CLI(new ServerCommunicator("localhost", 5005));

    public CLITest() throws IOException {
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testMainLoop() {

    }

    public void testParse() {
        cli.changeState(new DisconnectedState(cli));
        String[] args = cli.parseInput("move 1 2 abc");
        assertEquals("move", args[0]);
        assertEquals("1", args[1]);
        assertEquals("2", args[2]);
        assertEquals("abc", args[3]);
    }
}