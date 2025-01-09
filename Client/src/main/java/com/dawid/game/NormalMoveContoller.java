package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Assumptions: if there are only 2 players: player 1 fights player 4
 * Assumptions: if there are only 3 players: players 1, 3 and 5 are present
 * Assumptions: if there are only 4 players: 1 vs 4 and 2 vs 5
 */
public class NormalMoveContoller implements IMoveController {
    int numberOfPlayers;
    Board board;

    public NormalMoveContoller(Board board, int numberOfPlayers) {
        this.board = board;
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setupPawns() {
        ArrayList<Integer> players = new ArrayList<>();
        if(numberOfPlayers == 3) {
            players.add(1);
            players.add(3);
            players.add(5);
        } else {
            if(numberOfPlayers >= 2) {
                players.add(1);
                players.add(4);
            }
            if(numberOfPlayers >= 4) {
                players.add(2);
                players.add(5);
            }
            if(numberOfPlayers >= 6) {
                players.add(3);
                players.add(6);
            }
        }

        for(Integer player : players) {
            System.out.println(player);
            Collection<Field> homeFields = board.getHomeFields(player);
            System.out.println(homeFields);
            for(Field field : homeFields) {
                field.setPawn(player);
            }
        }
    }

    @Override
    public Collection<Field> getPossibleMoves(Field startField) {
        Collection<Field> possibleMoves = new ArrayList<>();
        Collection<Field> neighboringFields = board.getNeighboringFields(startField);
        for (Field field : neighboringFields) {
            if(field.getPawn() != -1) {
                possibleMoves.add(field);
            }
        }
        return possibleMoves;
    }
}
