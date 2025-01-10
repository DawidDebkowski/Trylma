package com.dawid.gui;

import com.dawid.states.States;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StartController extends BaseController{
    @FXML
    protected void onConnectButtonClick() {
        SceneManager.setScene(States.MENU);
    }

    @FXML
    protected void onExitButtonClick() {
        print("Exiting Application!");
    }
}