package com.dawid.game;

import com.dawid.server.Lobby;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class OrderOfChaosVariant implements GameVariant {
    public void initializeGame(Lobby lobby) {
        DavidStarBoard board = (DavidStarBoard) lobby.getBoard();
        List<Field> nonHomeFields = board.getNonHomeFields();
        Collection<Integer> playerNumbers = board.getPlayerNumbers(lobby.getPlayerCount());
        List<Field> playerHomeFields = new ArrayList<>();
        for(int player: playerNumbers) {
            playerHomeFields.addAll(board.getHomeFields(player));
        }
        Random random = new Random();
        for(Field field: playerHomeFields) {
            Field field2 = nonHomeFields.get(random.nextInt(nonHomeFields.size()));
            lobby.notifyAll("Moved: Player " + field.getHome() + " " + board.getCoordinates(field) + " " + board.getCoordinates(field2));
            nonHomeFields.remove(field2);
        }

    }
}