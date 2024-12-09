package com.dawid.states;

/**
 * Class needed for creating EnumMaps.
 */
public interface ICommand {
    void execute(String[] args) throws IllegalArgumentException;
}