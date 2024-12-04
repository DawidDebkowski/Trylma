package com.dawid;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//TODO: test this class when the server is online
public class ServerCommunicator implements ITrylmaProtocol {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;


    ServerCommunicator(String serverAdress, int port) throws IOException {
        socket = new Socket(serverAdress, port);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }


    @Override
    public boolean move(String from, String to) {
        out.println("MOVE" + from + " " + to);
        String response = in.nextLine();
        if(response.equals("VALID_MOVE")) {
            return true;
        }
        return false;
    }
}
