package com.dawid.gui;

import com.dawid.States;
import com.dawid.gui.controllers.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*** TODO: read this
 * Singleton (not yet, to be refactored) to change scenes.
 *
 * Klasa powstała, żeby zmieniać pliki .fxml w jakiś sensowy i zorganizowany sposób.
 * Napisana w połowie przez gpt, mam nadzieje, że usuniemy ten komentarz w przyszłych commitach i nikt go nie zobaczy.
 * Klasa sama w sobie działa dobrze, wywołanie można zobaczyć w StartController.
 *
 */
public class SceneManager {
    private static Stage stage;
    private static final Map<States, String> scenes = new HashMap<>();
    private static ClientGUI client;

    public static void initialize(Stage primaryStage, ClientGUI client) {
        SceneManager.client = client;
        stage = primaryStage;

        // Wczytanie scen
        scenes.put(States.DISCONNECTED, "disconnectedScene.fxml");
        scenes.put(States.MENU, "menuScene.fxml");
        scenes.put(States.PLAYING, "gameScene.fxml");
        scenes.put(States.LOBBY, "lobbyScene.fxml");

        stage.setTitle("Trylma Chinese Checkers by Ćmolud (TM)");
        stage.show();
    }

    /**
     * Changing client states (main scenes) should only be done by
     * the server and the client. Don't call this in the controllers.
     * Changes the scene based on the state.
     * @param state state that has a scene
     * @return controller of the new scene
     */
    public static IController setScene(States state) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(scenes.get(state)));
            Scene scene = new Scene(loader.load());
            IController controller = loader.getController();
            controller.setClient(client);
            controller.lateInitialize();
            stage.setScene(scene);
//            stage.sizeToScene();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}