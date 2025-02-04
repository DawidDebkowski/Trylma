package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Assumptions: if there are only 2 players: player 1 fights player 4
 * Assumptions: if there are only 3 players: players 1, 3 and 5 are present
 * Assumptions: if there are only 4 players: 1 vs 4 and 2 vs 5
 */
public class NormalVariantController implements IVariantController {
    int numberOfPlayers;
    Board board;
    private Field overField;
    List<Player> players;

    public NormalVariantController(Board board, int numberOfPlayers) {
        this.board = board;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Notatka: ta cudowna metoda robi liste graczy, bo 1 i 4 sa naprzeciwko siebie, wiec jezeli jest 2 graczy to
     * oni musza grac przeciwko sobie
     * @return
     */
    public List<Player> setupPlayers() {
        players = new ArrayList<>();
        if(numberOfPlayers == 3) {
            players.add(new Player(1, 4));
            players.add(new Player(3, 6));
            players.add(new Player(5, 2));
        } else {
            if(numberOfPlayers >= 2) {
                players.add(new Player(1, 4));
                players.add(new Player(4, 1));
            }
            if(numberOfPlayers >= 4) {
                players.add(new Player(2, 5));
                players.add(new Player(5, 2));
            }
            if(numberOfPlayers >= 6) {
                players.add(new Player(3, 6));
                players.add(new Player(6, 3));
            }
        }
        return players;
    }

    /**
     * Spawns pawns in player home regions.
     */
    public void setupPawns(List<Player> players) {
        for(Player player : players) {
            System.out.println(player);
            Collection<Field> homeFields = board.getHomeFields(player.homeField);
            System.out.println(homeFields);
            for(Field field : homeFields) {
                field.setPawn(player.homeField);
            }
        }
    }


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
     * Sets given pawn in given coordinates.
     *
     * @param pawn
     * @param sx
     * @param sy
     * @param fx
     * @param fy
     */
    @Override
    public void movePawn(int pawn, int sx, int sy, int fx, int fy) {
        board.getField(sx, sy).setPawn(0);
        board.getField(fx, fy).setPawn(pawn);

    }

    @Override
    public Player checkWin() {
        for (Player player : players) {
            Collection<Field> homeFields = board.getHomeFields(player.winField);
            int count = 0;
            for (Field field : homeFields) {
                if(field.getPawn() == player.homeField) {
                    count++;
                }
            }
            if(count == homeFields.size()) {
                return player;
            }
        }
        return null;
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
            if(field == null) continue;
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
