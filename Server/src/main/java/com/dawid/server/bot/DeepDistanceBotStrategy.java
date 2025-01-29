package com.dawid.server.bot;

import com.dawid.game.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This doesnt quite work yet
 * stuck at the last couple of moves
 */
public class DeepDistanceBotStrategy implements IBotStrategy {
    private final int SKIP_PENALTY = 100;
    private int skipLimit = 0;
    private BotPlayer botPlayer;
    private Board board;
    private GameEngine gameEngine;
    private Coordinates[] lastTargetMove;

    /**
     * BAD STRATEGY - CANT WIN
     * @return
     */
    @Override
    public Coordinates[] calculateMove() {
        int width = 3;
        int depth = 2;
        Coordinates[] finalMove = new Coordinates[2];
        Coordinates[][] finalMoves = new Coordinates[width][2];

        Collection<Field> startFields = board.getPlayerFields(botPlayer.getNumber());

        for (int i = 0; i < width; i++) {
            finalMoves[i] = bestFromFields(startFields);
            if(finalMoves[i][0] == null || finalMoves[i][1] == null) {continue;}
            startFields.remove(board.getField(finalMoves[i][0]));
        }

        for (int i = 0; i < width; i++) {
            if(finalMoves[i][0] == null || finalMoves[i][1] == null) {continue;}
            startFields.add(board.getField(finalMoves[i][0]));
        }

        int bestMoveIndex = 1;
        int bestScore = 0;
        for (int i = 0; i < width; i++) {
            if(finalMoves[i][0] == null || finalMoves[i][1] == null) {
                finalMoves[i][0] = new Coordinates(-1, -1);
                finalMoves[i][1] = new Coordinates(-1, -1);
                if(bestScore == 0) {
                    bestMoveIndex = i;
                }
                continue;
            }

            startFields.remove(board.getField(finalMoves[i][0]));
            startFields.add(board.getField(finalMoves[i][1]));
            finalMove = bestFromFields(startFields);
            int currentScore = getMoveValue(board.getField(finalMoves[i][0]), board.getField(finalMoves[i][1]));
            if(finalMoves[i][0] != null && finalMoves[i][1] != null) {
                currentScore += getMoveValue(board.getField(finalMove[0]), board.getField(finalMove[1]));
            }
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestMoveIndex = i;
            }

            startFields.add(board.getField(finalMoves[i][0]));
            startFields.remove(board.getField(finalMoves[i][1]));
        }

        finalMove = finalMoves[bestMoveIndex];

        if(lastTargetMove != null && Coordinates.isEqual(lastTargetMove[0], finalMove[0]) && Coordinates.isEqual(lastTargetMove[1], finalMove[1])) {
            skipLimit++;
        } else {
            skipLimit = 0;
        }
        lastTargetMove = finalMove;
        return finalMove;
    }

    private Coordinates[] bestFromFields(Collection<Field> startFields) {
        Coordinates[] finalMove = new Coordinates[2];

        //let's allow for backwards moves if the depth makes it worth it
        int bestScore = -10;
        if(skipLimit > 2) {bestScore -= SKIP_PENALTY; System.out.println("Skip limit reached + " + botPlayer.getNumber());}
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
        // out of home push
        if(startField.getHome() == botPlayer.getNumber()) {
            foreignHomePenalty += -5;
        } else if(startField.getHome() == botPlayer.getWinFieldID()) {
            //walking clueless in win home penalty
            if(homeDistanceDelta <= 0) {
                foreignHomePenalty += 10;
            }
            // MOVING OUT OF YOUR WINFIELD
            if(targetField.getHome() != botPlayer.getWinFieldID()) {
                foreignHomePenalty += 50;
            }
        }
        //enter win
        if(startField.getHome() == 0) {
            if(targetField.getHome() == botPlayer.getWinFieldID()) {
                foreignHomePenalty += -50;
            }
        }

        // foreign home penalty
        if(targetField.getHome() != 0) {
            if(targetField.getHome() != botPlayer.getWinFieldID() && targetField.getHome() != botPlayer.getNumber()) {
                foreignHomePenalty += 5;
            }
        }

        // repeating moves penalty
        if(lastTargetMove != null) {
            if(Coordinates.isEqual(lastTargetMove[0], board.getCoordinates(targetField)) && Coordinates.isEqual(lastTargetMove[1], board.getCoordinates(startField))) {
                sameMovePenalty = 100;
//                System.out.println("sameMovePenalty: " + sameMovePenalty);
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
