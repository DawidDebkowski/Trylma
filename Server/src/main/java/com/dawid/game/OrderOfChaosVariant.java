package com.dawid.game;

import com.dawid.server.Lobby;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class OrderOfChaosVariant implements GameVariant {
    public void initializeGame(Lobby lobby) {
        System.out.println("Order of Chaos variant - intializing game");
        DavidStarBoard board = (DavidStarBoard) lobby.getBoard();
        List<Field> nonHomeFields = board.getNonHomeFields();
        Collection<Integer> playerNumbers = board.getPlayerNumbers(lobby.getPlayerCount());
        System.out.println("Player count: " + lobby.getPlayerCount());
        System.out.println("Player numbers: " + playerNumbers);
        List<Field> playerHomeFields = new ArrayList<>();
        for(int player: playerNumbers) {
            playerHomeFields.addAll(board.getHomeFields(player));
            System.out.println("Player " + player + " home fields: " + board.getHomeFields(player));
        }
        System.out.println("Player home fields: " + playerHomeFields.size());
        Random random = new Random();
        for(Field field: playerHomeFields) {
            Field field2 = nonHomeFields.get(random.nextInt(nonHomeFields.size()));
            lobby.notifyAll("Moved: Player " + field.getHome() + " MOVE " + board.getCoordinates(field) + " " + board.getCoordinates(field2));
            System.out.println("Moved: Player MOVE " + field.getHome() + " MOVE " + board.getCoordinates(field) + " " + board.getCoordinates(field2));
            nonHomeFields.remove(field2);
        }

    }
    public Variant getVariant() {
        return Variant.ORDER_CHAOS;
    }
}