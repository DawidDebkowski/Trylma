package com.dawid.gui;

import com.dawid.game.Variant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class LobbySceneController extends BaseController {
    @FXML
    ComboBox<String> variants;

    @Override
    public void lateInitialize() {
        super.lateInitialize();
        for(Variant variant : Variant.values()) {
            variants.getItems().add(variant.getName());
        }

    }

    @FXML
    private void startGame() {
        client.sendStartGameRequest();
    }

    @FXML
    private void setVariant() {
        client.getSocket().setVariant(variants.getValue());
    }

}
