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
//                    System.out.println("Adding field " + i + " " + j);
                }
            }
        }

        System.out.println("start home fields");

        Collection<Field> homeFields = board.getHomeFields(getNumber());
        Collection<Field> winFields = board.getHomeFields(getWinFieldID());

        System.out.println("start home fields");
        Field f1 = calculateFirstHomeField(board, homeFields);
        Field f2 = calculateFirstHomeField(board, winFields);
        System.out.println("end home fields " + board.getCoordinates(f1) + ", " + board.getCoordinates(f2));

        calculateDistance(board, f1, 0, BotField::setHomeDistance, (bot, disc) -> {return  bot.getHomeDistance();});
        calculateDistance(board, f2, 0, BotField::setWinDistance, (bot, disc) -> {return  bot.getWinDistance();});

        printDistances();
    }

    private Field calculateFirstHomeField(Board board, Collection<Field> homeFields) {
        Field out = null;
        int min = 1000;
        for(Field field : homeFields) {
            int count = 0;
            Collection<Field> neighbors = board.getNeighboringFields(field);
            for(Field neighbor : neighbors) {
                if(neighbor != null) {count++;}
            }
            if(count < min) {
                out = field;
                min = count;
            }
        }

        return out;
    }

    private void printDistances() {
        for (BotField[] botFields : boardOverlay) {
            for (BotField botField : botFields) {
                if (botField == null) {
                    System.out.print("   ");
                } else {
                    System.out.print(botField.getHomeDistance() + " " + botField.getWinDistance());
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
