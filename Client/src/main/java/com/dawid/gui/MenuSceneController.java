package com.dawid.gui;

import com.dawid.game.LobbyInfo;
import com.dawid.game.Variant;
import com.dawid.states.States;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Notatka: Trzeba to zintegrować z ServerCommunicatorem, na razie nie mam pomysłu jak
 *
 * Problemem jest to ze ServerCommunicator a dokladniej ObserverCommunicator, czyli klasa w srodku pierwszego
 * musialaby znac albo MenuController i wtedy mu rozkazywac co jest sprzeczne pewnie z kazda mozliwa zasada
 * albo klient powinen miec cos w stylu
 * addLobby(dane)
 * ale to tez duzo roboty, posredniczenia i wydaje sie bez sensu
 * a poza tym ten problem sie bedzie powtarzal, bo w srodku lobby tez potrzebne beda
 * te same informacje, bo na pewno bedziemy miec scene gdy jestes przed rozpoczeciem juz w lobby
 * i tam te same info bedzie potrzebne
 * i pewnie z gra beda podobne problemy
 *
 * zastanawialem sie czy moze jakis obserwator pattern nie mialby moze sensu to znaczy
 * moznaby sie podlaczyc pod sluchanie konkretnych informacji od Servera poprzez ObserverCommunicator
 * cos w stylu
 * ListenFor("Move")
 * ListenFor("Lobbyinfo")
 * i wtedy ten ObserverCommunicator wysylalby info do wszystkich sluchaczy, ale by ich oczywiscie nie znal
 * ale wtedy to glupio zeby to bylo "Move" itp lepiej zeby byl enum do tego.
 * wiec to tez duzo roboty nie wiem czy warto.
 *
 * no i nie wiem do konca jak zrobic gre
 */
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
        client.getSocket().getLobbyInfo();
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
        client.getSocket().create();
        SceneManager.setScene(States.LOBBY);
    }

}
