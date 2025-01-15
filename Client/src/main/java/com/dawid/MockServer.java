package com.dawid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MockServer extends ServerCommunicator {
    public MockServer() {
        super();
        setInputOutput(new BufferedReader(new InputStreamReader(System.in)),
                new PrintWriter(System.err, true));
        connected = true;
        initObserver();
    }

    @Override
    public void setClient(IClient client) {
        super.setClient(client);
    }

    @Override
    public void disconnect() {
       connected = false;
    }
}
