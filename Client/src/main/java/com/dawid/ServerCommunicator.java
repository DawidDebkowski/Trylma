package com.dawid;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerCommunicator implements IServerCommands {
    private  Socket socket;
    private  BufferedReader in;
    private  PrintWriter out;
    private IServerClient client;
    protected boolean connected;

    public ServerCommunicator(String serverAdress, int port) throws IOException {
        connect(serverAdress, port);
        initObserver();
    }
    public ServerCommunicator(IServerClient client) {
        setClient(client);
    }
    public ServerCommunicator() {}
    protected void setInputOutput(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }
    public void setClient(IServerClient client) {
        this.client = client;
    }
    @Override
    public void connect(String serverAdress, int port) throws IOException {
        socket = new Socket(serverAdress, port);
        setInputOutput(new BufferedReader(new InputStreamReader(socket.getInputStream())),
                new PrintWriter(socket.getOutputStream(), true));
        connected = true;
        initObserver();
    }
    protected void  initObserver() {
        ObserverCommunicator observer = new ObserverCommunicator();
        Thread observerThread = new Thread(observer);
        observerThread.start();
        System.out.print("Connected and listening\n");
    }
    /**
     * Right now the thread throws connection error. I don't know how to fix it.
     */
    @Override
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

            protocol.put("Connected", (message) -> client.changeState(States.MENU));
            protocol.put("Created", (message) -> client.changeState(States.LOBBY));
            protocol.put("Joined", (message) -> client.changeState(States.LOBBY));
            protocol.put("Left", (message) -> client.changeState(States.MENU));
            protocol.put("Started", this::receiveStart);
            protocol.put("Moved:", this::receiveMove);
            protocol.put("Lobbies:", this::receiveLobbies);
            protocol.put("TURN", this::receiveTurn);
            protocol.put("Loaded:", (message) -> client.changeState(States.SAVED_LOBBY));
            protocol.put("Saved:", this::receiveSave);

        }

        // "started" myID maxPlayers Variant
        private void receiveStart(String[] args) {
            int playerID = Integer.parseInt(args[1]);
            int maxPlayers = Integer.parseInt(args[2]);
            Board board = new DavidStarBoard();
            Variant variant = Variant.getVariantByName(args[3]);
            client.startGame(playerID, board, variant, maxPlayers);
        }

        private void receiveSave(String[] args) {
            client.message("Game saved with ID " + args[1]);
        }

        private void receiveTurn(String[] args) {
            client.setMyTurn();
        }

        private void receiveMove(String[] args) {
            // Moved: Player x MOVE c1 c2
            client.moveOnBoard(Integer.parseInt(args[2]), args[4], args[5]);
            System.out.println("Got move on from " + args[2] + " " + args[4] + " to " + args[5]);
        }
        private void receiveLobbies(String[] args) {
            var lobbies = new ArrayList<LobbyInfo>();
            try {
                while (true) {
                    String line = in.readLine();
                    if(line.equals("END")) break;
                    var lobbyInfo = line.split(" ");
                    lobbies.add(new LobbyInfo(Integer.parseInt(lobbyInfo[0]), Integer.parseInt(lobbyInfo[1]), Variant.getVariantByName(lobbyInfo[2]), Integer.parseInt(lobbyInfo[3])));
                    System.out.println("Received: " + line);
                }
            } catch (IOException e) {
                System.out.println("Error reading lobbies");
            }
            client.updateLobbies(lobbies);
        }

        @Override
        public void run() {
            System.err.println("Server communicator started");
            while(connected){
                try {
                    String message = in.readLine();
                    if(message == null){continue;}
                    Platform.runLater(() -> System.out.println("Received: " + message));
                    String[] args = message.split(" ");
                    if(args.length == 0){
                        return;
                    }
                    System.err.println("\n[Server]: " + message);
                    client.message("\n[Server]: " + message);
                    if(protocol.containsKey(args[0])){
                        protocol.get(args[0]).execute(args);
                    }
                } catch (IOException e) {
                    System.out.println("Connection error");
                }
            }
        }
    }

    @Override
    public void move(int sx, int sy, int fx, int fy) {
        out.println("MOVE" + " " + sx + "_" + sy + " " + fx + "_" + fy);
    }

    @Override
    public void join(int lobbyID) {
        out.println("JOIN" + " " + lobbyID);
    }

    @Override
    public void leaveLobby() {
        out.println("LEAVE");
    }

    @Override
    public void startGame() {
        out.println("START");
    }

    @Override
    public void create() {
        out.println("CREATE");
    }

    @Override
    public void getLobbyInfo() {
        out.println("LOBBYINFO");
    }
    @Override
    public void setVariant(String variant) {
        out.println("VARIANT " + variant);
    }
    @Override
    public void setMaxPlayers(int maxPlayers) {
        out.println("MAX_PLAYERS " + maxPlayers);
    }

    @Override
    public void loadSavedGame(String savedId) {
        out.println("LOAD " + savedId);
    }

    @Override
    public void saveGame() {
        out.println("SAVE");
    }
}
