package com.dawid.game;

/**
 * Represents a game variant.
 */
public interface GameVariant {
    /**
     * Initializes the game, doing stuff specific to the variant.
     * @param lobby The lobby in which the game is played.
     */
    public void initializeGame(Lobby lobby);
    /**
     * Returns the variant of the game.
     * @return The variant of the game.
     */
    public Variant getVariant();
}