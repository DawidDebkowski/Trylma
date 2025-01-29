package com.dawid.game;

/*
    * Represents the standard variant of the game.
 */
public class StandardVariant implements GameVariant {
    @Override
    public void initializeGame(Lobby lobby) {
        return;
    }
    @Override
    public Variant getVariant() {
        return Variant.NORMAL;
    }
}