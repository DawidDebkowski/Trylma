package com.dawid;

import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.states.MenuState;
import com.dawid.states.LobbyState;
import com.dawid.states.PlayingState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ServerCommunicator{
    private  Socket socket;
    private  BufferedReader in;
    private  PrintWriter out;
    private IClient client;
    protected boolean connected;

    public ServerCommunicator(String serverAdress, int port) throws IOException {
        connect(serverAdress, port);
        initObserver();
    }
    public ServerCommunicator() {}
    protected void setInputOutput(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }
    public void setClient(IClient client) {
        this.client = client;
    }
    public void connect(String serverAdress, int port) throws IOException {
        socket = new Socket(serverAdress, port);
        setInputOutput(new BufferedReader(new InputStreamReader(socket.getInputStream())),
                new PrintWriter(socket.getOutputStream(), true));
        connected = true;
    }
    protected void  initObserver() {
        ObserverCommunicator observer = new ObserverCommunicator();
        Thread observerThread = new Thread(observer);
        observerThread.start();
        System.out.printf("Connected and listening\n");
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
    interface IResponse {
        void execute(String[] args);
    }
    class ObserverCommunicator extends Thread {

        HashMap<String, IResponse> protocol;

        ObserverCommunicator() {
            protocol = new HashMap<>();

            protocol.put("Created", (IResponse) (message) -> {client.changeState(new LobbyState(client));});
            protocol.put("Joined", (IResponse) (message) -> {client.changeState(new LobbyState(client));});
            protocol.put("Left", (IResponse) (message) -> {client.changeState(new MenuState(client));});
            protocol.put("Started", (IResponse) (message)  -> {client.changeState(new PlayingState(client));});
            protocol.put("Moved:", this::receiveMove);
            protocol.put("Lobbies:", this::receiveLobbies);
        }

        private void receiveMove(String[] args) {
            // Moved: Player x MOVE c1 c2
            client.moveOnBoard(Integer.parseInt(args[2]), args[4], args[5]);
            System.out.println("Got move on from " + args[2] + " " + args[4] + " to " + args[5]);
        }
        private void receiveLobbies(String[] args) {
            var lobbies = new ArrayList<LobbyInfo>();
            try {
                var line = in.readLine();
                while (!line.equals("END")) {
                    var lobbyInfo = line.split(" ");
                    lobbies.add(new LobbyInfo(Integer.parseInt(lobbyInfo[0]), Integer.parseInt(lobbyInfo[1]), Variant.getVariantByName(lobbyInfo[2])));
                }
            } catch (IOException e) {
                System.out.println("Error reading lobbies");
            }
            client.updateLobbies(lobbies);
        }

        @Override
        public void run() {
            while(connected){
                try {
                    String message = in.readLine();
                    //TODO: remove in production XD
                    System.out.println("Received: " + message);
                    String[] args = message.split(" ");
                    if(args.length == 0){
                        return;
                    }
                    System.err.println("\n[Server]: " + message);
                    //client.message("\n[Server]: " + message);
                    if(protocol.containsKey(args[0])){
                        protocol.get(args[0]).execute(args);
                    }
                    client.prompt();
                } catch (IOException e) {
                    System.out.println("Connection error");
                }
            }
        }
    }

    public boolean move(int sx, int sy, int fx, int fy) {
        out.println("MOVE" + " " + sx + "-" + sy + " " + fx + "-" + fy);
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

    public void getLobbyInfo() {
        out.println("LOBBYINFO");
    }
    public void setVariant(String variant) {
        out.println("VARIANT: " + variant);
    }
}
