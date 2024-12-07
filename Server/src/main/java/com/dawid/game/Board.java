package com.dawid.game;

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
     * Returns the neighboring fields of the given field.
     * @param field
     * @return The neighboring fields of the given field.
     */
    public Collection<Field> getNeighboringFields(Field field);

    /**
     * Returns the fields of the home region of the given player.
     * @param player The player.
     * @return The fields of the home region of the given player.
     */
    public Collection<Field> getHomeFields(int player);

    /**
     * Get the field that we will land on after jump
     * @param start the field we currently are on
     * @param across the field we are jumping across, this should be a neighbor of start
     * @return the field that we will land on after jump, null if the field does not exist
     * @throws IllegalArgumentException if the fields are not neighbors or do not exist
     */
    public Field getJumpField(Field start, Field across) throws IllegalArgumentException;
}
