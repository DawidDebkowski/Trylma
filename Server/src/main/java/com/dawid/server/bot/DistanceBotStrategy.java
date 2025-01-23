package com.dawid.server.bot;

import com.dawid.game.*;

import java.util.ArrayList;
import java.util.Collection;

public class DistanceBotStrategy implements IBotStrategy {
    private BotPlayer botPlayer;
    private Board board;
    private GameEngine gameEngine;

    @Override
    public Coordinates[] calculateMove() {
        Coordinates[] finalMove = new Coordinates[2];

        Collection<Field> startFields = board.getPlayerFields(botPlayer.getNumber());
        int bestScore = 0;
        for (Field startField : startFields) {
            if(startField == null) {continue;}

            Collection<Coordinates> possibleC = gameEngine.getPossibleMoves(board.getCoordinates(startField));
            Collection<Field> possibleMoves = new ArrayList<>();
            for(Coordinates c : possibleC) {
                possibleMoves.add(board.getField(c.getRow(), c.getColumn()));
            }

            for (Field possibleField : possibleMoves) {
                if(possibleField==null) {continue;}

                int moveValue = getMoveValue(startField, possibleField);
                if(moveValue >= bestScore) {
                    finalMove[0] = board.getCoordinates(startField);
                    finalMove[1] = board.getCoordinates(possibleField);
                    bestScore = moveValue;
                }
            }
        }
        if(finalMove[0] == null || finalMove[1] == null) {
            finalMove[0] = new Coordinates(-1, -1);
            finalMove[1] = new Coordinates(-1, -1);
        }

        return finalMove;
    }

    private int getMoveValue(Field startField, Field targetField) {
        BotField startBot = botPlayer.getBotField(startField);
        BotField targetBot = botPlayer.getBotField(targetField);
        return (targetBot.getHomeDistance() - startBot.getHomeDistance())* 2 - (targetBot.getWinDistance() - startBot.getWinDistance());
    }

    @Override
    public void setup(BotPlayer player, GameEngine gameEngine, Board board) {
        botPlayer = player;
        this.board = board;
        this.gameEngine = gameEngine;
    }
}
