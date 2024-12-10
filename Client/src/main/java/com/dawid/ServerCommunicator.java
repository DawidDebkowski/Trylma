package com.dawid;

import com.dawid.states.DisconnectedState;
import com.dawid.states.LobbyState;
import com.dawid.states.PlayingState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

//TODO: test this class when the server is online
public class ServerCommunicator{
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private CLI client;
    private boolean connected;

    ServerCommunicator(String serverAdress, int port) throws IOException {
        socket = new Socket(serverAdress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        connected = true;

        ObserverCommunicator observer = new ObserverCommunicator();
        Thread observerThread = new Thread(observer);
        observerThread.start();
        System.out.printf("Connected and listening to %s:%d\n", serverAdress, port);
    }

    public void setClient(CLI client) {
        this.client = client;
    }

    /**
     * Right now the thread throws connection error. I don't know how to fix it.
     */
    public void disconnect() {
        connected = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection");
        }
    }

    /**
     * Thread reading messages from server.
     * Changes states based on messages.
     */
    class ObserverCommunicator extends Thread implements Runnable{
        HashMap<String, Runnable> protocol;

        ObserverCommunicator() {
            protocol = new HashMap<>();

            protocol.put("Created", (Runnable) () -> {client.changeState(new LobbyState(client));});
            protocol.put("Joined", (Runnable) () -> {client.changeState(new LobbyState(client));});
            protocol.put("Left", (Runnable) () -> {client.changeState(new DisconnectedState(client));});
            protocol.put("Started", (Runnable) () -> {client.changeState(new PlayingState(client));});
        }

        @Override
        public void run() {
            while(connected){
                try {
                    String message = in.readLine();
                    String[] args = message.split(" ");
                    if(args.length == 0){
                        return;
                    }
                    System.out.printf("\n[Server]: %s\n", message);
                    Runnable r = protocol.get(args[0]);
                    if(r != null){
                        r.run();
                    }
                    client.prompt();
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
