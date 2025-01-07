package com.dawid.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static Stage stage;
    private static final Map<String, Scene> scenes = new HashMap<>();

    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("JavaFX Application");

        // Wczytanie scen
        loadScene("preGame", "/com/example/pre_game.fxml");
        loadScene("game", "/com/example/game.fxml");

        stage.show();
    }

    private static void loadScene(String name, String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(resourcePath));
            Scene scene = new Scene(loader.load());
            scenes.put(name, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            stage.setScene(scene);
        } else {
            System.err.println("Scene '" + name + "' not found.");
        }
    }
}