package com.dawid.server;

import com.dawid.game.*;

import java.util.*;

/**
 * Represents a lobby.
 */
public class Lobby {
    // We could use state pattern, but it's not necessary if only 2 states are possible
    private boolean inGame = false;
    private Board board;
    private TurnController turnController;
    private final List<Player> players;
    private int maxPlayers; //TODO: add to lobbyinfo
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
    /**
     * Notifies all players in the lobby.
     * @param message The message to send.
     */
    public void notifyAll(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }
    /**
     * Returns the number of players in the lobby.
     * @return The number of players in the lobby.
     */
    public int getPlayerCount() {
        return players.size();
    }
    /**
     * Adds a player to the lobby.
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
        player.setLobby(this);
    }
    /**
     * Removes a player from the lobby.
     * @param player The player to remove.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }
    /**
     * Starts the game.
     */
    public void startGame() {
        // make bots to the desired number of players
        for(int i=0;i<maxPlayers-getPlayerCount();i++) {
            BotPlayer bot = new BotPlayer(System.out);
//            Scanner in = new Scanner(socket.getInputStream());
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            while (in.hasNextLine()) {
//                clientInputHandler.exec(in.nextLine());
//            }
        }
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
            turnController.getCurrrentPlayer().makeTurn();
        }
        else {
            notifyAll("ERROR: Incorrect number of players");
        }
    }
    /**
     * Ends the game.
     */
    public void endGame() {
        inGame = false;
    }

    /**
     * Makes a move in the game.
     * @param player The player making the move.
     * @param args The arguments of the move.
     */
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
        turnController.getCurrrentPlayer().makeTurn();
    }
    /**
     * Returns the variant of the game.
     */
    public Variant getVariant() {
        return variant.getVariant();
    }
    /**
     * Returns the board of the game.
     */
    public Board getBoard() {
        return board;
    }
    /**
     * Sets the variant of the game.
     * @param variant The variant of the game.
     */
    public void setVariant(Variant variant) {
        this.variant = Variant.getGameVariant(variant);
    }
}
