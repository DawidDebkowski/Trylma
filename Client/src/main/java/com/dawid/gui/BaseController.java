package com.dawid.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseController implements IController {
    @FXML
    protected Label logLabel;
    protected GUI client;

    @Override
    public void print(String message) {
        logLabel.setText(message);
    }

    @Override
    public void setClient(GUI client) {
        this.client = client;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void refresh() {

    }
}
