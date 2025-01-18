package com.dawid.gui;

import com.dawid.States;
import com.dawid.gui.controllers.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to change scenes.
 * It is mostly based on the States enum.
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
//            controller.refresh();
//            stage.sizeToScene();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}