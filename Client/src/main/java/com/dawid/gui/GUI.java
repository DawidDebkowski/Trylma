package com.dawid.gui;

import com.dawid.IClient;
import com.dawid.MockServer;
import com.dawid.ServerCommunicator;
import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.game.GameEngine;
import com.dawid.game.NormalMoveController;
import com.dawid.game.LobbyInfo;
import com.dawid.states.ClientState;
import com.dawid.states.States;
import javafx.application.Application;
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

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.initialize(stage, this);
        SceneManager.setScene(States.DISCONNECTED);
        //TODO: change to
        communicator = new ServerCommunicator();
//        communicator = new MockServer();
        lobbies = new ArrayList<>();

        communicator.setClient(this);
    }

    private void launchGame() {
        DavidStarBoard board = new DavidStarBoard();
        gameEngine = new GameEngine(board, new NormalMoveController(board,3), 1);
        controller = SceneManager.setScene(States.PLAYING);
        gameEngine.startGame();
        controller.refresh();
    }

    public static void main(String[] args) {
        launch();
    }

    public void connect(String serverAdress, int port) throws IOException {
        communicator.connect(serverAdress, port);
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
        SceneManager.setScene(newState.getName());
    }

    @Override
    public void exit() {

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

    public void sendStartGameRequest() {
        communicator.startGame();
    }
}