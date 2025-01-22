package com.dawid.server;

import com.dawid.game.Board;
import com.dawid.game.BotField;
import com.dawid.game.Coordinates;
import com.dawid.game.Field;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BotPlayer extends Player{
    private BotField[][] boardOverlay;
    /**
     * Creates a new player.
     *
     * @param out The output stream to send messages to the player.
     */
    public BotPlayer(OutputStream out) {
        super(out);
    }


    /**
     * Nie wiem, czy jestem niewiarygodnie głupi,
     * czy po prostu tak to musiało być zrobione
     */
    public void setupBoard(Board board) {
        System.out.println("Setup board");
        Field[][] fields = board.getBoardStateCopy();
        boardOverlay = new BotField[board.getHeight()][board.getWidth()];
        System.out.println("Setup board1");
        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[i].length; j++) {
                BotField field = new BotField();
                if(fields[i][j] != null) {
                    field.setHomeDistance(100000);
                    field.setWinDistance(100000);
                    boardOverlay[i][j] = field;
                    System.out.println("Adding field " + i + " " + j);
                }
            }
        }

        calculateDistance(board, board.getField(0,12), 0, BotField::setHomeDistance, (bot, disc) -> {return  bot.getHomeDistance();});
        calculateDistance(board, board.getField(0,12), 0, BotField::setWinDistance, (bot, disc) -> {return  bot.getWinDistance();});

        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[i].length; j++) {
                if(boardOverlay[i][j] == null) {System.out.print("   ");}
                else {
                    System.out.print(boardOverlay[i][j].getHomeDistance() + " " + boardOverlay[i][j].getWinDistance());
                }
            }
            System.out.println();
        }
    }

    private void calculateDistance(Board board, Field start, int distance, BiConsumer<BotField, Integer> distanceSetter, BiFunction<BotField, Integer, Integer> distanceGetter) {
        try {
            Coordinates startC = board.getCoordinates(start);
            distanceSetter.accept(boardOverlay[startC.getRow()][startC.getColumn()], distance);
//            System.out.println("Calculated distance " + distance + " for " + startC);
            Collection<Field> neighbours = board.getNeighboringFields(start);
            for(Field field : neighbours) {
                if(field != null) {
                    Coordinates fieldC = board.getCoordinates(field);
                    int newDistance = distanceGetter.apply(boardOverlay[fieldC.getRow()][fieldC.getColumn()], -1);
//                    System.out.println("Calculated new distance " + newDistance + " for " + fieldC);
                    if(newDistance <= distance) {continue;}
                    calculateDistance(board, field, distance+1, distanceSetter, distanceGetter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeTurn() {
        String[] args = {"MOVE", "-1_-1", "-1_-1"};

        getLobby().makeMove(this, args);
    }

    private void createBoardOverlay(Board board) {

    }

}
