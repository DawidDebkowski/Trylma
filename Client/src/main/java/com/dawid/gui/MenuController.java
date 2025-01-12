package com.dawid.gui;

import com.dawid.game.Variant;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

public class MenuController extends BaseController{
    Collection<LobbyBox> lobbyBoxes;
    @FXML
    protected VBox lobbyHolder;

    @FXML
    protected void onJoinButtonClick() {
        print("Joined");
    }

    public void initialize() {
        super.initialize();
        lobbyBoxes = new ArrayList<>();
        startRefresh();
        addLobby(1, 2, 6, Variant.normal);
        showLobbies();
    }

    public void startRefresh() {
        lobbyBoxes.clear();
        lobbyHolder.getChildren().clear();
    }

    public void addLobby(int id, int currentPlayers, int maxPlayers, Variant variant) {
        LobbyBox lb = new LobbyBox(client, id, currentPlayers, maxPlayers, variant);
        lobbyBoxes.add(lb);
    }

    public void showLobbies() {
        for (LobbyBox lb : lobbyBoxes) {
            lobbyHolder.getChildren().add(lb);
        }
    }
}
