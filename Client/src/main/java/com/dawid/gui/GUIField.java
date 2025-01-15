package com.dawid.gui;

import com.dawid.game.Coordinates;
import com.dawid.game.Field;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Collection;

/**
 * Handles drag and drop
 * Check with GameEngine if it can move
 * then send a move to the server through ServerCommunicator
 *
 * Actual movement will be handled by the client and refreshing the board.
 */
public class GUIField extends Circle {
    private final Field field;
    private final GameSceneController controller;
    private Paint lastColor;
    int row, column;

    public GUIField(Field myField, int row, int column, GameSceneController gameController) {
        super();
        this.field = myField;
        this.row = row;
        this.column = column;
        this.controller = gameController;
        this.setRadius(20);

        // Enable drag detection
        this.setOnDragDetected(event -> {
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            if(gameController.getGameEngine().isMyPawn(field.getPawn())) {
                content.putString(row + "_" + column); // Assuming field has a method getId()
                db.setContent(content);
                Collection<Coordinates> coordinates = gameController.getGameEngine().getPossibleMoves(new Coordinates(row, column));
                gameController.highlight(coordinates);
            }
            event.consume();
        });

        // Handle drag over
        this.setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Handle drag entered
        this.setOnDragEntered(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                lastColor = this.getFill();
                this.setFill(Color.LIGHTGREEN);
            }
            event.consume();
        });

        // Handle drag exited
        this.setOnDragExited(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
//                this.setFill(GameSceneController.playerColors.get(field.getPawn()));
                this.setFill(lastColor);
            }
            event.consume();
        });

        // Handle drag dropped
        this.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String sourceFieldId = db.getString();
                Coordinates c = Coordinates.fromString(sourceFieldId);
                System.out.println("Moved from field " + sourceFieldId + " to field " + row + "-" + column);
                boolean canMove = controller.getGameEngine().tryMove(Coordinates.fromString(sourceFieldId), new Coordinates(row, column));
                if(canMove) {
                    controller.issueMove(c.getRow(), c.getColumn(), row, column);
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Handle drag done
        this.setOnDragDone(DragEvent::consume);
    }

    public void refresh() {
        this.setFill(GameSceneController.playerColors.get(field.getPawn()));
        this.setStroke(GameSceneController.playerColors.get(field.getHome()));
    }
}
