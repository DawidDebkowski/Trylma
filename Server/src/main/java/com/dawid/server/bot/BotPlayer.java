package com.dawid.server.bot;

import com.dawid.game.*;
import com.dawid.server.Player;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BotPlayer extends Player {
    private BotField[][] boardOverlay;
    private GameEngine gameEngine;
    private IBotStrategy botStrategy;
    private Board board;
    /**
     * Creates a new player.
     *
     * @param out The output stream to send messages to the player.
     */
    public BotPlayer(OutputStream out, GameEngine gameEngine, IBotStrategy strategy)  {
        super(out);
        this.botStrategy = strategy;
        this.gameEngine = gameEngine;
    }


    /**
     * Nie wiem, czy jestem niewiarygodnie głupi,
     * czy po prostu tak to musiało być zrobione
     */
    public void setupBoard(Board board) {
        this.board = board;
        botStrategy.setup(this, gameEngine, board);

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

//        System.out.println("start home fields");

        Collection<Field> homeFields = board.getHomeFields(getNumber());
        Collection<Field> winFields = board.getHomeFields(getWinFieldID());

//        System.out.println("start home fields");
        Field f1 = calculateFirstHomeField(homeFields);
        Field f2 = calculateFirstHomeField(winFields);
//        System.out.println("end home fields " + board.getCoordinates(f1) + ", " + board.getCoordinates(f2));

        calculateDistance(f1, 0, BotField::setHomeDistance, (bot, disc) -> {return  bot.getHomeDistance();});
        calculateDistance(f2, 0, BotField::setWinDistance, (bot, disc) -> {return  bot.getWinDistance();});

        printDistances();
    }

    private Field calculateFirstHomeField(Collection<Field> homeFields) {
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

    private void calculateDistance(Field start, int distance, BiConsumer<BotField, Integer> distanceSetter, BiFunction<BotField, Integer, Integer> distanceGetter) {
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
                calculateDistance(field, distance+1, distanceSetter, distanceGetter);
            }
        }
    }

    @Override
    public void makeTurn() {
        Coordinates[] moveField = botStrategy.calculateMove();
        String[] args = {"MOVE", moveField[0].toString(), moveField[1].toString()};
        getLobby().makeMove(this, args);
    }

    BotField[][] getBoardOverlay() {return boardOverlay;}

    BotField getBotField(Field field) {
        return boardOverlay[board.getCoordinates(field).getRow()][board.getCoordinates(field).getColumn()];
    }
}
