package com.dawid.gui.controllers;

import com.dawid.IClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseController implements IController {
    @FXML
    protected Label logLabel;
    protected IClient client;

    @Override
    public void print(String message) {
        logLabel.setText(message);
        System.out.println("LOG: " + message);
    }

    @Override
    public void setClient(IClient client) {
        this.client = client;
    }

    @Override
    public void lateInitialize() {

    }

    @Override
    public void refresh() {

    }
}
