package com.dawid.gui;

import com.dawid.IClient;
import com.dawid.ServerCommunicator;
import com.dawid.game.*;
import com.dawid.states.ClientState;
import com.dawid.states.States;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class GUI extends Application implements IClient {
    private ServerCommunicator communicator;
    private ClientState state;
    private IController controller;
    private Collection<LobbyInfo> lobbies;
    private GameEngine gameEngine;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        SceneManager.initialize(stage, this);
        SceneManager.setScene(States.DISCONNECTED);

        startGame(1, new DavidStarBoard(), Variant.NORMAL, 6);
        communicator = new ServerCommunicator();
//        communicator = new MockServer();
        lobbies = new ArrayList<>();

        communicator.setClient(this);

//        communicator.join(0);
//        communicator.startGame();
//        moveOnBoard(1, "0_12", "7_13");
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
        gameEngine.makeMove(player, sx, sy, fx, fy);
        controller.refresh();
        System.out.println("Moved on client " + from + " to " + to);
    }

    @Override
    public Board getBoard() {
        return gameEngine.getBoard();
    }

    public GameEngine getGameController() {
        return gameEngine;
    }

    @Override
    public ServerCommunicator getSocket() {
        return communicator;
    }

    @Override
    public void changeState(ClientState newState) {
//        SceneManager.setScene(newState.getState());
    }

    @Override
    public void exit() {

    }

    @Override
    public void startGame(int myID, Board board, Variant variant, int playerCount) {
        launchGame(myID, board, variant, playerCount);
    }

    private void launchGame(int myID, Board board, Variant variant, int playerCount) {
        if(variant == Variant.NORMAL) {
            gameEngine = new GameEngine(board, new NormalMoveController(board,playerCount), myID);
        }
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
    public void prompt() {
        return;
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
    public void myTurn() {
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

    public void sendStartGameRequest() {
        communicator.startGame();
    }

    public void stageToScene() {
        stage.sizeToScene();
    }
}