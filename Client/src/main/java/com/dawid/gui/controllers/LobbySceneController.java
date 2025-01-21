package com.dawid.gui.controllers;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.game.Variant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class LobbySceneController extends BaseController {
    @FXML
    ComboBox<String> variants;
    @FXML
    ComboBox<Integer> numberOfPlayersComboBox;

    @Override
    public void lateInitialize() {
        super.lateInitialize();
        for(Variant variant : Variant.values()) {
            variants.getItems().add(variant.getName());
        }
        //TODO: to raczej trzeba zmienic, bo to obrzydliwe
        Board board = new DavidStarBoard();
        numberOfPlayersComboBox.getItems().
                addAll(board.getPossiblePlayerCounts());


    }

    @FXML
    private void startGame() {
        client.getServerCommands().startGame();
    }

    @FXML
    private void setVariant() {
        client.getServerCommands().setVariant(variants.getValue());
    }

    @FXML
    private void setMaximumNumberOfPlayers() {
        client.getServerCommands().setMaxPlayers(numberOfPlayersComboBox.getValue());
    }

}
