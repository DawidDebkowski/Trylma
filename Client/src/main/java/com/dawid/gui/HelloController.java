package com.dawid.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onConnectButtonClick() {
        welcomeText.setText("Connected to JavaFX Application!");
    }

    @FXML
    protected void onExitButtonClick() {
        welcomeText.setText("Exiting Application!");
    }
}