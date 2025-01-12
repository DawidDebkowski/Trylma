package com.dawid.gui;

import com.dawid.game.Variant;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class LobbyBox extends HBox {
    private final GUI client;
    private final int id;
    private final int currentPlayers;
    private final int maxPlayers;
    private final Variant variant;

    public LobbyBox(GUI client, int id, int currentPlayers, int maxPlayers, Variant variant) {
        this.client = client;
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.variant = variant;
    }

    public void show() {
        Label playersLabel = new Label("Players: " + currentPlayers + " / " + maxPlayers);
        Label name = new Label("Lobby: " + id);
        Label variantLabel = new Label("Variant: " + variant.toString());

        Button joinButton = new Button("Join");
        joinButton.setOnAction(event -> {
            client.getSocket().join(id);
        });

        getChildren().addAll(name, playersLabel, variantLabel, joinButton);
    }
}
