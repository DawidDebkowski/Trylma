package com.dawid.gui;

import com.dawid.game.Board;
import com.dawid.game.IBoardListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class GameController extends BaseController implements IBoardListener {
    @FXML
    private BorderPane mainBorderPane;

    private GridPane mainGrid;
    private GUIField[][] fields;

    static Map<Integer, Color> playerColors;

    public GameController() {}

    public void lateInitialize() {
        super.lateInitialize();
        playerColors = new HashMap<>();
        playerColors.put(0, Color.BLACK);
        playerColors.put(1, Color.GREEN);
        playerColors.put(2, Color.BLUE);
        playerColors.put(3, Color.RED);
        playerColors.put(4, Color.PURPLE);
        playerColors.put(5, Color.YELLOW);
        playerColors.put(6, Color.PINK);
        intialiseBoard(client.getBoard());
        mainBorderPane.setCenter(mainGrid);
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(0);
        mainGrid.setVgap(10);
    }

    public void intialiseBoard(Board board) {
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
                    GUIField circle = new GUIField(board.getField(i, j));
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
    }

    @Override
    public void recieveChange(int newPawn, int x, int y) {
        return;
    }
}
