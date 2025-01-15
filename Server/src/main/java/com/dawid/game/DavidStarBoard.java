package com.dawid.game;

import java.util.*;

/**
 * Represents the board of the game. It is a David's star.
 * Maybe we'll use it later
 */
public class DavidStarBoard implements Board {
    private final int height = 17;
    private final int width = 25;
    private final int homeRegionSize = 4;
    private Field[][] board;
    private Map<Integer, List<Field>> homeFields;
    private Map<Field, Coordinates> coordinates;
    private final List<Integer> correct_no_of_players = Arrays.asList(2, 3, 4, 6);

    public DavidStarBoard() {
        homeFields = new HashMap<>();
        initializeBoard();
        initializeCoordinates();
    }
    @Override
    public Field getField(int row, int column) throws IllegalArgumentException {
        if(row <= 0 || row > height) {
            throw new IllegalArgumentException("Invalid row");
        }
        int counter = 0;
        for(int i = 0; i < width; i++) {
            if(board[row][i] != null) {
                counter++;
            }
            if(counter == column) {
                return board[row-1][i];
            }
        }
        throw new IllegalArgumentException("Invalid column");

    }
    @Override
    public Collection<Field> getNeighboringFields(Field field) {
        Coordinates c = coordinates.get(field);
        List<Field> neighbors = new ArrayList<>();
        int[][] neighborsDelta = {
                {0, 2},
                {0, -2},
                {1, 1},
                {1, -1},
                {-1, -1},
                {-1, 1}
        };
        for (int[] delta : neighborsDelta) {
            try {
                neighbors.add(board[c.getRow() + delta[0]][c.getColumn() + delta[1]]);
            } catch (ArrayIndexOutOfBoundsException e) {
                //Do nothing
            }
        }
        return neighbors;
    }

    @Override
    public Collection<Field> getHomeFields(int player) {
        return homeFields.get(player);
    }
    @Override
    public Field getJumpField(Field start, Field across) throws IllegalArgumentException {
        Coordinates startCoordinates = coordinates.get(start);
        Coordinates acrossCoordinates = coordinates.get(across);

        if (Math.abs(startCoordinates.getColumn() - acrossCoordinates.getColumn()) == 2 &&
                startCoordinates.getRow() == acrossCoordinates.getRow()) {
            try {
                return board[startCoordinates.getRow()][2*acrossCoordinates.getColumn()-startCoordinates.getColumn()];
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }

        }
        if (Math.abs(startCoordinates.getColumn() - acrossCoordinates.getColumn()) == 1 &&
                Math.abs(startCoordinates.getRow() - acrossCoordinates.getRow()) == 1) {
            int dx = acrossCoordinates.getRow() - startCoordinates.getRow();
            int dy = acrossCoordinates.getColumn() - startCoordinates.getColumn();
            try {
                return board[acrossCoordinates.getRow() + dx][acrossCoordinates.getColumn() + dy];
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }

        }
        throw new IllegalArgumentException("The fields are not neighbors");
    }
    @Override
    public boolean correctPlayerCount(int playerCount) {
        return correct_no_of_players.contains(playerCount);
    }

    @Override
    public Collection<Integer> getPlayerNumbers(int playerCount) {
        if(playerCount == 2) {
            return Arrays.asList(1, 4);
        }
        if(playerCount == 3) {
            return Arrays.asList(1, 3, 5);
        }
        if(playerCount == 4) {
            return Arrays.asList(1, 3, 4, 6);
        }
        if(playerCount == 6) {
            return Arrays.asList(1, 2, 3, 4, 5, 6);
        }
        return null;
    }
    public List<Field> getNonHomeFields() {
        List<Field> nonHomeFields = new ArrayList<>();
        for (Field[] row : board) {
            for (Field field : row) {
                if (field != null && field.getHome() == -1) {
                    nonHomeFields.add(field);
                }
            }
        }
        return nonHomeFields;
    }
    public Coordinates getCoordinates(Field field) {
        return coordinates.get(field);
    }

    private void initializeCoordinates() {
        coordinates = new HashMap<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] != null) {
                    coordinates.put(board[i][j], new Coordinates(i, j));
                }
            }
        }
    }
    private void initializeBoard() {
        board  = new Field[height][width];
        int triangleHeight = 13;
        // The david's star can be divided into 2 overlapping triangles,
        // so this is how I will initialize the board
        for (int i = 0; i < triangleHeight; i++) {
            for (int j = width/2-i; j <= width/2+i; j+=2) {
                board[i][j] = new Field();
            }
        }
        for (int i = 0; i < triangleHeight; i++) {
            for (int j = width/2-i; j <= width/2+i; j+=2) {
                board[height-1-i][j] = new Field();
            }
        }
        // Now I will create the home regions (yikes)
        createHome(0, width/2, false, 1);
        createHome(2*homeRegionSize-1, width-homeRegionSize, true, 2);
        createHome(2*homeRegionSize+1, width-homeRegionSize, false, 3);
        createHome(height-1, width/2, true, 4);
        createHome(2*homeRegionSize+1, homeRegionSize-1, false, 5);
        createHome(2*homeRegionSize-1, homeRegionSize-1, true, 6);

    }
    private void createHome(int row, int column, boolean reversed, int player) {
        homeFields.put(player, new ArrayList<>());
        if (reversed) {
            for (int i = 0; i < homeRegionSize; i++) {
                for (int j = column -i; j <= column +i; j+=2) {
                    board[row -i][j].setHome(player);
                    homeFields.get(player).add(board[row -i][j]);

                }
            }
        } else {
            for (int i = 0; i < homeRegionSize; i++) {
                for (int j = column -i; j <= column +i; j+=2) {
                    board[row +i][j].setHome(player);
                }
            }
        }

    }
    //Dev purposes
    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j].getHome());
                }
            }
            System.out.println();
        }
    }
}

