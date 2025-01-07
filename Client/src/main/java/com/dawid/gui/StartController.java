package com.dawid.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onConnectButtonClick() {
        SceneManager.setScene("lobby");
    }

    @FXML
    protected void onExitButtonClick() {
        welcomeText.setText("Exiting Application!");
    }
}