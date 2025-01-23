package com.dawid.server.bot;

import com.dawid.game.*;

import java.util.ArrayList;
import java.util.Collection;

public class DistanceBotStrategy implements IBotStrategy {
    private BotPlayer botPlayer;
    private Board board;
    private GameEngine gameEngine;
    private Coordinates lastTargetMove;

    @Override
    public Coordinates[] calculateMove() {
        Coordinates[] finalMove = new Coordinates[2];

        Collection<Field> startFields = board.getPlayerFields(botPlayer.getNumber());

        finalMove = bestFromFields(startFields);

        if(finalMove[0] == null || finalMove[1] == null) {
            finalMove[0] = new Coordinates(-1, -1);
            finalMove[1] = new Coordinates(-1, -1);
        }

        lastTargetMove = finalMove[0];
        return finalMove;
    }

    private Coordinates[] bestFromFields(Collection<Field> startFields) {
        Coordinates[] finalMove = new Coordinates[2];

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
        return finalMove;
    }

    private int getMoveValue(Field startField, Field targetField) {
        BotField startBot = botPlayer.getBotField(startField);
        BotField targetBot = botPlayer.getBotField(targetField);
        int homeDistanceDelta = targetBot.getHomeDistance() - startBot.getHomeDistance();
        int winDistanceDelta = targetBot.getWinDistance() - startBot.getWinDistance();
        int foreignHomePenalty = 0;
        int sameMovePenalty = 0;
        if(targetField.getHome() != 0) {
            if(targetField.getHome() != botPlayer.getWinFieldID() && targetField.getHome() != botPlayer.getNumber()) {
                foreignHomePenalty = 10;
            }
        }
        if(lastTargetMove != null) {
            if(lastTargetMove.getRow() == board.getCoordinates(targetField).getRow() && lastTargetMove.getColumn() == board.getCoordinates(targetField).getColumn()) {
                sameMovePenalty = 150;
                System.out.println("sameMovePenalty: " + sameMovePenalty);
            }
        }
        return homeDistanceDelta * 2 - winDistanceDelta - foreignHomePenalty - sameMovePenalty;
    }

    @Override
    public void setup(BotPlayer player, GameEngine gameEngine, Board board) {
        botPlayer = player;
        this.board = board;
        this.gameEngine = gameEngine;
    }
}
