package com.dawid.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("disconnectedScene.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Trylma [disconnected]!");
//        stage.setScene(scene);
//        stage.show();
        SceneManager.initialize(stage);
        SceneManager.setScene("disconnected");
    }

    public static void main(String[] args) {
        launch();
    }
}