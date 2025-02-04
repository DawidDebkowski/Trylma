package com.dawid.gui.controllers;

import com.dawid.States;
import com.dawid.gui.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;

public class DisconnectedSceneController extends BaseController{
    @FXML
    protected void onConnectButtonClick() {
        try {
            client.connect("localhost", 5005);
        } catch (IOException e) {
            print("Failed to connect to the server");
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }
}