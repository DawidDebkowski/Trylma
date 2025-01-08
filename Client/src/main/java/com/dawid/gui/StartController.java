package com.dawid.gui;

import com.dawid.states.States;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onConnectButtonClick() {
        SceneManager.setScene(States.MENU);
    }

    @FXML
    protected void onExitButtonClick() {
        welcomeText.setText("Exiting Application!");
    }
}