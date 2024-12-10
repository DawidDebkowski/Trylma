package com.dawid;

import com.dawid.states.LobbyState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

//TODO: test this class when the server is online
public class ServerCommunicator{
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private CLI client;

    ServerCommunicator(String serverAdress, int port) throws IOException {
        socket = new Socket(serverAdress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        ObserverCommunicator observer = new ObserverCommunicator();
        Thread observerThread = new Thread(observer);
        observerThread.start();
        System.out.printf("Connected and listening to %s:%d\n", serverAdress, port);
    }

    public void setClient(CLI client) {
        this.client = client;
    }

    /**
     * Thread reading messages from server.
     * Changes states based on messages.
     */
    class ObserverCommunicator extends Thread implements Runnable{
        HashMap<String, Runnable> protocol;

        ObserverCommunicator() {
            protocol = new HashMap<>();

            protocol.put("Created lobby", (Runnable) () -> {client.changeState(new LobbyState(client));});
            protocol.put("Joined lobby", (Runnable) () -> {client.changeState(new LobbyState(client));});
            protocol.put("VALID_MOVE", (Runnable) () -> {client.changeState(new LobbyState(client));});
        }

        @Override
        public void run() {
            while(true){
                try {
                    String message = in.readLine();
                    System.out.println(message);
                    if(message == null){
                        System.out.println("Server stopped for some reason");
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Connection error");
                }
            }
        }
    }

    public boolean move(String from, String to) {
        out.println("MOVE" + " " + from + " " + to);
        return true;
    }

    public boolean join(int lobbyID) {
        out.println("JOIN" + " " + lobbyID);
        return true;
    }

    public void leaveLobby() {
        out.println("LEAVE");
    }

    public boolean startGame() {
        out.println("START");
        return true;
    }

    public boolean create() {
        out.println("CREATE");
        return true;
    }

    private String awaitResponse() {
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            System.out.println("zerwane połączenie");
        }
        System.out.println(response);
        return response;
    }
}
