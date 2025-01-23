package com.dawid.server.bot;

import com.dawid.game.Board;
import com.dawid.game.Coordinates;
import com.dawid.game.Field;
import com.dawid.game.GameEngine;

public class SkipBotStrategy implements IBotStrategy{
    private BotPlayer botPlayer;
    private Board board;

    @Override
    public Coordinates[] calculateMove() {
        Coordinates[] move = new Coordinates[2];
        move[0] = new Coordinates(-1, -1);
        move[1] = new Coordinates(-1, -1);
        return move;
    }

    @Override
    public void setup(BotPlayer player, GameEngine gameEngine, Board board) {
        botPlayer = player;
        this.board = board;
    }
}
