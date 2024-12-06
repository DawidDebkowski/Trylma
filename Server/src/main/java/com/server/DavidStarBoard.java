package com.server;

import java.util.*;

public class DavidStarBoard implements Board {
    private final int height = 17;
    private final int width = 25;
    private final int homeRegionSize = 4;
    private final int triangleHeight = 13;
    private Field[][] board;
    private Map<Integer, List<Field>> homeFields;
    //Temporary
    public static void main(String[] args) {
        DavidStarBoard board = new DavidStarBoard();
        board.initializeBoard();
        board.printBoard();
    }
    public DavidStarBoard() {
        homeFields = new HashMap<>();
        initializeBoard();
    }
    private void initializeBoard() {
        board  = new Field[height][width];
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
        createHome(0, width/2, false, 1);
        createHome(2*homeRegionSize-1, width-homeRegionSize, true, 2);
        createHome(2*homeRegionSize+1, width-homeRegionSize, false, 3);
        createHome(height-1, width/2, true, 4);
        createHome(2*homeRegionSize+1, homeRegionSize-1, false, 5);
        createHome(2*homeRegionSize-1, homeRegionSize-1, true, 6);

    }
    private void createHome(int y, int x, boolean reversed, int player) {
        homeFields.put(player, new ArrayList<>());
        if (reversed) {
            for (int i = 0; i < homeRegionSize; i++) {
                for (int j = x-i; j <= x+i; j+=2) {
                    board[y-i][j].setHome(player);
                    homeFields.get(player).add(board[y-i][j]);

                }
            }
        } else {
            for (int i = 0; i < homeRegionSize; i++) {
                for (int j = x-i; j <= x+i; j+=2) {
                    board[y+i][j].setHome(player);
                }
            }
        }

    }
    //Temporary
    private void printBoard() {
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
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
    //TODO
    @Override
    public Collection<Field> getNeighboringFields(Field field) {
        return null;
    }

    @Override
    public Collection<Field> getHomeFields(int player) {
        return homeFields.get(player);
    }
}

