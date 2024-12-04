package com.dawid;

import junit.framework.TestCase;

public class CLITest extends TestCase {
    CLI cli = new CLI();

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testMainLoop() {

    }

    public void testParse() {
        cli.ChangeState(new PlayingState());
        String[] args = cli.parseInput("move 1 2 abc");
        assertEquals("move", args[0]);
        assertEquals("1", args[1]);
        assertEquals("2", args[2]);
        assertEquals("abc", args[3]);
    }
}