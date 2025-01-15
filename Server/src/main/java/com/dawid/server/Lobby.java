package com.dawid.server;

import com.dawid.game.Board;
import com.dawid.game.DavidStarBoard;
import com.dawid.game.TurnController;
import com.dawid.game.Variant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Lobby {
    // We could use state pattern, but it's not necessary if only 2 states are possible
    private boolean inGame = false;
    private Board board;
    private TurnController turnController;
    private final List<Player> players;
    private Variant variant;
    public Lobby(List<Player> players) {
        this.players = players;
        this.variant = Variant.NORMAL;
    }
    public Lobby(Variant variant) {
        this.players = new ArrayList<>();
        //only DavidStarBoard is implemented
        board = new DavidStarBoard();
        this.variant = variant;
    }
    public void notifyAll(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }
    public int getPlayerCount() {
        return players.size();
    }
    public void addPlayer(Player player) {
        players.add(player);
        player.setLobby(this);
    }
    public void removePlayer(Player player) {
        players.remove(player);
    }
    public void startGame() {
        if(board.correctPlayerCount(this.getPlayerCount())) {
            inGame = true;
            notifyAll("Started game");
            turnController = new TurnController(players);
            Collection<Integer> playerNumbers = board.getPlayerNumbers(this.getPlayerCount());
            Iterator<Integer> iterator = playerNumbers.iterator();
            for(Player player : players) {
                player.setNumber(iterator.next());
            }
            turnController.getCurrrentPlayer().sendMessage("TURN");
        }
        else {
            notifyAll("ERROR: Incorrect number of players");
        }
    }
    public void endGame() {
        inGame = false;
    }

    public void makeMove(Player player, String[] args) {
        if(!inGame) {
            player.sendMessage("ERROR: Game not started");
        }
        if(!player.equals(turnController.getCurrrentPlayer())) {
            player.sendMessage("ERROR: Not your turn");
            return;
        }
        player.getLobby().notifyAll("Moved: Player " + player.getNumber() + " " + String.join(" ", args));
        turnController.nextTurn();
        turnController.getCurrrentPlayer().sendMessage("TURN");
    }
    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
}
