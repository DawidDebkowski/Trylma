package com.dawid.gui;

import com.dawid.IClient;
import com.dawid.ServerCommunicator;
import com.dawid.game.Board;
import com.dawid.states.ClientState;
import com.dawid.states.States;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application implements IClient {
    private ServerCommunicator communicator;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.initialize(stage);
        SceneManager.setScene(States.DISCONNECTED);
    }

    public static void main(String[] args) {
        launch();
    }

    public void connect(String serverAdress, int port) {
        try {
            communicator = new ServerCommunicator(serverAdress, port);
        } catch (IOException e) {

        }
    }

    @Override
    public void moveOnBoard(int player, String x, String y) {

    }

    @Override
    public Board getBoard() {
        return null;
    }

    @Override
    public ServerCommunicator getSocket() {
        return null;
    }

    @Override
    public void changeState(ClientState newState) {
        SceneManager.setScene(newState.getName());
    }

    @Override
    public void exit() {

    }

    @Override
    public void prompt() {

    }
}