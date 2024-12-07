package com.dawid.game;

public class Coordinates {
    private int row;
    private int column;
    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public boolean equals(Coordinates c) {
        return row == c.getRow() && column == c.getColumn();
    }
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
