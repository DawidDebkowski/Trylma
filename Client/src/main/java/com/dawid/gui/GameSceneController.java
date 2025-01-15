package com.dawid.gui;

import com.dawid.game.Board;
import com.dawid.game.Coordinates;
import com.dawid.game.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for GameScene
 *
 * Build a GUI board. It should in theory work with any board.
 * Will have the same Coordinates as a board.
 */
public class GameSceneController extends BaseController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    protected Rectangle turnIndicator;

    private GridPane mainGrid;
    private GUIField[][] fields;
    private Collection<GUIField> lastHightlited;

    static Map<Integer, Color> playerColors;

    private GameEngine gameEngine;

    public GameSceneController() {
    }

    public void lateInitialize() {
        super.lateInitialize();
        gameEngine = client.getGameController();
        playerColors = new HashMap<>();
        playerColors.put(0, Color.BLACK);
        playerColors.put(1, Color.GREEN);
        playerColors.put(2, Color.BLUE);
        playerColors.put(3, Color.RED);
        playerColors.put(4, Color.PURPLE);
        playerColors.put(5, Color.YELLOW);
        playerColors.put(6, Color.PINK);
        initialiseBoard(client.getBoard());
        mainBorderPane.setCenter(mainGrid);
//        mainBorderPane.setPadding(new Insets(0));
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(0);
        mainGrid.setVgap(10);
        client.stageToScene();

    }

    public void initialiseBoard(Board board) {
        int width = board.getWidth();
        int height = board.getHeight();
        fields = new GUIField[height][width];

        mainGrid = new GridPane(width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    if(board.getField(i, j) == null) {
                        continue;
                    }
                    GUIField circle = new GUIField(board.getField(i, j), i, j, this);
                    fields[i][j] = circle;
                    mainGrid.add(circle, j, i);
                } catch (IllegalArgumentException e) {
                    //do nothing
                }
            }
        }
    }

    public void refresh() {
        super.refresh();
        for (GUIField[] fieldRow : fields) {
            for (GUIField guiField : fieldRow) {
                if(guiField != null) {
                    guiField.refresh();
                }
            }
        }
        if(gameEngine.isYourTurn()) {
            turnIndicator.setFill(Color.FORESTGREEN);
        } else {
            turnIndicator.setFill(Color.DARKRED);
        }
    }

    public void highlight(Collection<Coordinates> coordinates) {
        if(lastHightlited != null) {
            for (GUIField guiField : lastHightlited) {
                guiField.refresh();
            }
        }
        lastHightlited = new ArrayList<>();
        for (Coordinates c : coordinates) {
            GUIField guiField = fields[c.getRow()][c.getColumn()];
            if(guiField != null) {
                guiField.setFill(Color.BLUEVIOLET.brighter());
                lastHightlited.add(guiField);
            }
        }
    }

    @FXML
    public void onSkipButtonClicked(ActionEvent event) {
        if(gameEngine.isYourTurn()) {
            issueMove(-1, -1,-1, -1);
        }
    }

    void issueMove(int sx, int sy, int fx, int fy) {
        client.getSocket().move(sx, sy, fx, fy);
        getGameEngine().setMyTurn(false);
        refresh();
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
