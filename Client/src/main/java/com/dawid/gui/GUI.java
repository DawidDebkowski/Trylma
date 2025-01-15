package com.dawid.gui;

import com.dawid.IClient;
import com.dawid.ServerCommunicator;
import com.dawid.game.*;
import com.dawid.states.ClientState;
import com.dawid.states.States;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class GUI extends Application implements IClient {
    private ServerCommunicator communicator;
    private ClientState state;
    private IController controller;
    private Collection<LobbyInfo> lobbies;
    private GameEngine gameEngine;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.initialize(stage, this);
        connect("localhost", 5005);
//        communicator.create();
        communicator.join(0);
        communicator.startGame();
//        launchGame(1, new DavidStarBoard());
        SceneManager.setScene(States.DISCONNECTED);
    }

    public static void main(String[] args) {
        launch();
    }

    public void connect(String serverAdress, int port) throws IOException {
        communicator = new ServerCommunicator(serverAdress, port);
        communicator.setClient(this);
    }

    @Override
    public void moveOnBoard(int player, String from, String to) {
        String[] fromCoordinates = from.split("-");
        String[] toCoordinates = to.split("-");
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
        SceneManager.setScene(newState.getState());
    }

    @Override
    public void exit() {

    }

    @Override
    public void startGame(int myID, Board board, Variant variant, int playerCount) {
        launchGame(myID, board, variant, playerCount);
    }

    private void launchGame(int myID, Board board, Variant variant, int playerCount) {
//        DavidStarBoard board = new DavidStarBoard();
        if(variant == Variant.NORMAL) {
            gameEngine = new GameEngine(board, new NormalMoveController(board,playerCount), myID);
        }
        controller = SceneManager.setScene(States.PLAYING);
        gameEngine.startGame();
        controller.refresh();
    }

    @Override
    public void message(String message) {
        controller.print(message);
    }

    @Override
    public void prompt() {
        return;
    }

    @Override
    public void updateLobbies(Collection<LobbyInfo> lobbies) {
        this.lobbies = lobbies;
    }

    @Override
    public Collection<LobbyInfo> getLobbies() {
        return lobbies;
    }
}