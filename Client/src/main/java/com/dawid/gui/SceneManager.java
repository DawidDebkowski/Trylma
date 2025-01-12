package com.dawid.gui;

import com.dawid.states.States;
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
 *
 * Notatka: Tamto na gorze jest stare ale moze warte przeczytania.
 * Teraz ta klasa robi lazy-loading scen i to jest spoko.
 * Byc może dobrym pomyslem jest polaczenie starych statow z nowymi scenami.
 * Głowny problem jest taki ze stare staty przyjmuja String[] args, a budowanie stringow tylko po to
 * zeby przekazac informacje do serwera jest bez sensu, kiedy mozna wywylac client.getSocket().join(1) i tyle,
 * to join() to przyklad, chodzi o komenda(argumenty od razu w wymaganych typach)
 * Wiec nie wiem czy to robic czy nie zostawiam wolna reke
 */
public class SceneManager {
    private static Stage stage;
    private static final Map<States, String> scenes = new HashMap<>();
    private static GUI client;

    public static void initialize(Stage primaryStage, GUI client) {
        SceneManager.client = client;
        stage = primaryStage;
        stage.setTitle("JavaFX Application");

        // Wczytanie scen
        scenes.put(States.DISCONNECTED, "disconnectedScene.fxml");
        scenes.put(States.MENU, "menuScene.fxml");

        stage.setTitle("Trylma Chinese Checkers by Ćmolud (TM)");
        stage.show();
    }

    public static void setScene(States name) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(scenes.get(name)));
            Scene scene = new Scene(loader.load());
            IController controller = loader.getController();
            controller.setClient(client);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}