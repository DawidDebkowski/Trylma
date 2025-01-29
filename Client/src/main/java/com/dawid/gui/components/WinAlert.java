package com.dawid.gui.components;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class WinAlert {
    private int myId;

    public WinAlert(int id) {
        myId = id;
    }

    public String showAlert(int player) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if(player == myId) {
                alert.setTitle("WYGRAŁEŚ!");
            } else {
                alert.setTitle("Wygrał gracz " + player);
            }
            alert.setHeaderText("Gra będzie kontynuować");
            alert.setContentText("Gratulacje dla zwycięzcy");

            alert.showAndWait();
        });
        return "ok";
    }
}
