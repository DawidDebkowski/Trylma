package com.dawid.gui;

import com.dawid.game.Field;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

public class GUIField extends Circle {
    private Field field;
    private Label pawnLabel;

    public GUIField(Field myField) {
        super();
        this.field = myField;
        this.setRadius(20);
        pawnLabel = new Label(Integer.toString(field.getPawn()));

        this.setFill(GameController.playerColors.get(field.getPawn()));
        this.setStroke(GameController.playerColors.get(field.getHome()));

        System.out.println(field.getHome());
        System.out.println(GameController.playerColors.get(field.getHome()));
    }
}
