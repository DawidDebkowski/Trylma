package com.dawid.gui;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class GameController extends BaseController {
    @FXML
    private BorderPane mainBorderPane;

    private GridPane mainGrid;

    public GameController() {}

    public void initialize() {
        super.initialize();
        intialiseBoard(new DavidStarBoard());
        mainBorderPane.setCenter(mainGrid);
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(0);
        mainGrid.setVgap(10);
    }

    public void intialiseBoard(Board board) {
        int width = board.getWidth();
        int height = board.getHeight();

        mainGrid = new GridPane(width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    if(board.getField(i, j) == null) {
                        continue;
                    }
                    GUIField circle = new GUIField();
                    mainGrid.add(circle, j, i);
                } catch (IllegalArgumentException e) {
                    //do nothing
                }
            }
        }
    }
}
