package com.dawid.gui;

import com.dawid.*;
import com.dawid.game.*;
import com.dawid.gui.controllers.IController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ClientGUI extends Application implements IServerClient, IClient {
    private IServerCommands communicator;
    private IController controller;
    private Collection<LobbyInfo> lobbies;
    private GameEngine gameEngine;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            communicator.disconnect();
            Platform.exit();
        });
        SceneManager.initialize(stage, this);
        SceneManager.setScene(States.DISCONNECTED);

        communicator = new ServerCommunicator(this);
        lobbies = new ArrayList<>();

        connect("localhost", 5005);
        //test
        //communicator.create();
//        Thread.sleep(1000);
//        communicator.setMaxPlayers(6);
//        communicator.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void connect(String serverAdress, int port) throws IOException {
        communicator.connect(serverAdress, port);
    }

    @Override
    public void moveOnBoard(int player, String from, String to) {
        String[] fromCoordinates = from.split("_");
        String[] toCoordinates = to.split("_");
        int sx, sy, fx, fy;
        sx = Integer.parseInt(fromCoordinates[0]);
        sy = Integer.parseInt(fromCoordinates[1]);
        fx = Integer.parseInt(toCoordinates[0]);
        fy = Integer.parseInt(toCoordinates[1]);
        gameEngine.makeMoveFromServer(player, sx, sy, fx, fy);
        if(controller != null)
            controller.refresh();
        System.out.println("Moved on client " + from + " to " + to);
    }

    @Override
    public Board getBoard() {
        return gameEngine.getBoard();
    }

    @Override
    public IServerCommands getServerCommands() {
        return communicator;
    }

    @Override
    public void changeState(States newState) {
        Platform.runLater(() -> SceneManager.setScene(newState));
    }

    @Override
    public void exit() {
        communicator.disconnect();
        Platform.exit();
    }

    @Override
    public void startGame(int myID, Board board, Variant variant, int playerCount) {
        launchGame(myID, board, variant, playerCount);
    }

    private void launchGame(int myID, Board board, Variant variant, int playerCount) {
        IVariantController vc = null;
        if(variant == Variant.NORMAL) {
            vc = new NormalVariantController(board,playerCount);
        }
        else {
            vc = new NormalVariantController(board,playerCount);
        }
        gameEngine = new GameEngine(board, vc, myID, this);
        Platform.runLater(() -> {
            controller = SceneManager.setScene(States.PLAYING);
            assert controller != null;
            controller.refresh();
        });
        gameEngine.startGame();
    }

    @Override
    public void message(String message) {
//        controller.print(message);
        System.out.println(message);
    }

    @Override
    public void updateLobbies(Collection<LobbyInfo> lobbies) {
        this.lobbies = lobbies;
        Platform.runLater(() -> {
            if(controller != null)
                controller.refresh();
            else System.out.println("controller is null");
        });
    }

    @Override
    public void setMyTurn() {
        gameEngine.setMyTurn(true);
        Platform.runLater(() -> {
            if(controller != null)
                controller.refresh();
            else System.out.println("controller is null");
        });
    }

    @Override
    public Collection<LobbyInfo> getLobbies() {
        return lobbies;
    }

    public GameEngine getGameController() {
        return gameEngine;
    }
}