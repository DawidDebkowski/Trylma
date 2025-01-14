package com.dawid.gui;

import com.dawid.Commands;
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
    private ClientState state;
    private IController controller;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.initialize(stage, this);
        SceneManager.setScene(States.PLAYING);
    }

    public static void main(String[] args) {
        launch();
    }

    public void connect(String serverAdress, int port) throws IOException {
        communicator = new ServerCommunicator(serverAdress, port);
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
        return communicator;
    }

    @Override
    public void changeState(ClientState newState) {
        SceneManager.setScene(newState.getName());
    }

//    public void executeCommand(Commands command) {
//        state.executeCommand(command);
//    }

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
}