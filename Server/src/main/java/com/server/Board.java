package com.server;

import java.util.Collection;

public interface Board {
    /**
     * Returns the field at the given row and column.
     * @param row The row of the field.
     * @param column The column of the field.
     * @return The field at the given row and column.
     * @throws IllegalArgumentException If the row or column is invalid.
     */
    public Field getField(int row, int column) throws IllegalArgumentException;
    /**
     * Returns the height of the board.
     * @return The height of the board.
     */
    public int getHeight();
    /**
     * Returns the width of the board.
     * @return The width of the board.
     */
    public int getWidth();

    /**
     * Returns the neighboring fields of the given field.
     * @param field The field.
     * @return The neighboring fields of the given field.
     */
    public Collection<Field> getNeighboringFields(Field field);

    /**
     * Returns the fields of the home region of the given player.
     * @param player The player.
     * @return The fields of the home region of the given player.
     */
    public Collection<Field> getHomeFields(int player);
}
