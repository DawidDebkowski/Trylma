package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Assumptions: if there are only 2 players: player 1 fights player 4
 * Assumptions: if there are only 3 players: players 1, 3 and 5 are present
 * Assumptions: if there are only 4 players: 1 vs 4 and 2 vs 5
 */
public class NormalMoveController implements IMoveController {
    int numberOfPlayers;
    Board board;
    private Field overField;

    public NormalMoveController(Board board, int numberOfPlayers) {
        this.board = board;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Collection<Integer> setupPlayers() {
        Collection<Integer> players = new ArrayList<>();
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
        return players;
    }

    /**
     * Spawns pawns in player home regions.
     */
    public void setupPawns(Collection<Integer> players) {
        for(Integer player : players) {
            System.out.println(player);
            Collection<Field> homeFields = board.getHomeFields(player);
            System.out.println(homeFields);
            for(Field field : homeFields) {
                field.setPawn(player);
            }
        }
    }

    /**
     * Recursively searches for all possible moves from a given field. Includes all jumps.
     * TODO: clear field.visited
     * @param startField
     * @return
     */
    @Override
    public Collection<Field> getPossibleMoves(Field startField) {
        Collection<Field> possibleMoves = new ArrayList<>(); //make it a collection without duplicates?
        Collection<Field> neighboringFields = board.getNeighboringFields(startField);

        for (Field field : neighboringFields) {
            if (field == null) continue;

            if (field.getPawn() == 0) {
                possibleMoves.add(field);
            }
        }
        possibleMoves.addAll(getPossibleJumps(startField));

        //clear .visited
        for (Field field : possibleMoves) {
            field.visited = false;
        }

        return possibleMoves;
    }

    /**
     * Returns all possible jumps from a given field. Includes multi-jumping.
     * @param startField
     * @return
     */
    private Collection<Field> getPossibleJumps(Field startField) {
        Collection<Field> toCheck = new ArrayList<>();
        Collection<Field> possibleJumps = new ArrayList<>(); //make it a collection without duplicates?
        Collection<Field> neighboringFields = board.getNeighboringFields(startField);

        for (Field field : neighboringFields) {
            if(field.getPawn() != 0) {
                Field jumpField = board.getJumpField(startField, field);
                if (jumpField == null || jumpField.getPawn() != 0) continue;
                possibleJumps.add(jumpField);
                if (!jumpField.visited) {
                    jumpField.visited = true;
                    toCheck.add(jumpField); //BFS ?
                }
            }
        }
        // recursively add other possible moves
        for (Field field : toCheck) {
            System.out.println(field);
            possibleJumps.addAll(getPossibleJumps(field));
        }

        return possibleJumps;
    }
}
