package com.dawid.gui.controllers;

import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.gui.components.LobbyBox;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

public class MenuSceneController extends BaseController{
    Collection<LobbyBox> lobbyBoxes;
    @FXML
    protected VBox lobbyHolder;

    public void lateInitialize() {
        super.lateInitialize();
        lobbyBoxes = new ArrayList<>();
        startRefresh();
        for(var lobby: client.getLobbies()){
            addLobby(lobby.getId(), lobby.getCurrentPlayers(), lobby.getMaxPlayers(), lobby.getVariant());
        }
        showLobbies();
    }

    public void startRefresh() {
        client.getServerCommands().getLobbyInfo();
        lobbyBoxes.clear();
        lobbyHolder.getChildren().clear();
    }

    public void addLobby(int id, int currentPlayers, int maxPlayers, Variant variant) {
        LobbyBox lb = new LobbyBox(client, id, currentPlayers, maxPlayers, variant);
        lobbyBoxes.add(lb);
    }
    public void updateLobbyBoxes() {
        startRefresh();
        Collection<LobbyInfo> lobbies = client.getLobbies();
        for (LobbyInfo lobby : lobbies) {
            addLobby(lobby.getId(), lobby.getCurrentPlayers(), lobby.getMaxPlayers(), lobby.getVariant());
        }
    }

    public void showLobbies() {
        for (LobbyBox lb : lobbyBoxes) {
            lobbyHolder.getChildren().add(lb);
            lb.show();
        }
    }

    @Override
    public void refresh() {
        updateLobbyBoxes();
    }

    @FXML
    protected void onRefreshButtonClick() {
        updateLobbyBoxes();
        showLobbies();
    }
    @FXML
    protected void onNewGameClicked() {
        client.getServerCommands().create();
    }

}