package com.dawid.gui;

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
 * Na razie po prostu bawiłem się javafx, żeby zobaczyć, jakie mamy możliwości, możesz wszystko wyrzucić, jeżeli chcesz.
 * tyle przecinkow bo mam plugin poprawiajacy bledy lol
 */
public class SceneManager {
    private static Stage stage;
    private static final Map<String, Scene> scenes = new HashMap<>();

    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("JavaFX Application");

        // Wczytanie scen
        loadScene("disconnected", "disconnectedScene.fxml");
        loadScene("lobby", "lobbyScene.fxml");

        stage.setTitle("Trylma Chinese Checkers by Ćmolud (TM)");
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