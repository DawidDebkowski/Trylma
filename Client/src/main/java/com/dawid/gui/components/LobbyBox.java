package com.dawid.gui.components;

import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.cli.states.States;
import com.dawid.gui.ClientGUI;
import com.dawid.gui.SceneManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

/**
 * This represents one of the lobbies the player can join on menu scene.
 */
public class LobbyBox extends HBox {
    private final ClientGUI client;
    private final int id;
    private final int currentPlayers;
    private final int maxPlayers;
    private final Variant variant;

    public LobbyBox(ClientGUI client, int id, int currentPlayers, int maxPlayers, Variant variant) {
        this.client = client;
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.variant = variant;
    }
    public LobbyBox(LobbyInfo lobbyInfo, ClientGUI client) {
        this(client, lobbyInfo.getId(), lobbyInfo.getCurrentPlayers(), lobbyInfo.getMaxPlayers(), lobbyInfo.getVariant());
    }

    public void show() {
        this.setPadding(new Insets(30));
        this.setSpacing(40);

        Label playersLabel = new Label("Players: " + currentPlayers + " / " + maxPlayers);
        Label name = new Label("Lobby: " + id);
        Label variantLabel = new Label("Variant: " + variant.toString());

        Button joinButton = new Button("Join");
        joinButton.setOnAction(event -> {
            client.getServerCommunicator().join(id);
            SceneManager.setScene(States.LOBBY);
        });

        getChildren().addAll(name, playersLabel, variantLabel, joinButton);
    }
}
