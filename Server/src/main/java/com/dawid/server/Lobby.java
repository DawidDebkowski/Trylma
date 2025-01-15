package com.dawid.server;

import com.dawid.game.*;

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
    private GameVariant variant;
    public Lobby(List<Player> players) {
        this.players = players;
        board = new DavidStarBoard();
        this.variant = Variant.getGameVariant(Variant.NORMAL);
    }
    public Lobby(Variant variant) {
        this.players = new ArrayList<>();
        //only DavidStarBoard is implemented
        board = new DavidStarBoard();
        this.variant = Variant.getGameVariant(variant);
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
//            notifyAll("Started game");
            // players must know their numbers
            turnController = new TurnController(players);
            Collection<Integer> playerNumbers = board.getPlayerNumbers(this.getPlayerCount());
            Iterator<Integer> iterator = playerNumbers.iterator();
            for(Player player : players) {
                player.setNumber(iterator.next());
                player.sendMessage("Started " + player.getNumber() + " " + getPlayerCount() + " " + getVariant());
            }
            variant.initializeGame(this);
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
        return variant.getVariant();
    }
    public Board getBoard() {
        return board;
    }

    public void setVariant(Variant variant) {
        this.variant = Variant.getGameVariant(variant);
    }
}
