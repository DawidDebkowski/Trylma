package com.dawid.gui.controllers;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.game.Variant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class savedLobbySceneController extends BaseController {

    @Override
    public void lateInitialize() {
        super.lateInitialize();
    }

    @FXML
    private void startGame() {
        client.getServerCommands().startGame();
    }


}
