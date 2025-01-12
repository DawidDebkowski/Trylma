package com.dawid.gui;

import com.dawid.states.States;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;

public class StartController extends BaseController{
    @FXML
    protected void onConnectButtonClick() {
//        try {
//            client.connect("localhost", 5005);
//        } catch (IOException e) {
//            print("Failed to connect to the server");
//        }
        SceneManager.setScene(States.MENU);
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }
}