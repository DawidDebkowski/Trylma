package com.dawid.bot;

import com.dawid.game.Board;
import com.dawid.game.Coordinates;
import com.dawid.game.GameEngine;

public interface IBotStrategy {
    Coordinates[] calculateMove();
    void setup(BotPlayer player, GameEngine gameEngine, Board board);
}
