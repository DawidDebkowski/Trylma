package com.dawid.server;

import com.dawid.game.*;
import com.dawid.server.bot.BotPlayer;
import com.dawid.server.bot.DistanceBotStrategy;
import com.dawid.server.bot.DeepDistanceBotStrategy;

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
    private int maxPlayers;
    private GameVariant variant;
    private GameEngine gameEngine;

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
        maxPlayers = 6;
    }

    /**
     * Notifies all players in the lobby.
     *
     * @param message The message to send.
     */
    public void notifyAll(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    /**
     * Returns the number of players in the lobby.
     *
     * @return The number of players in the lobby.
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Adds a player to the lobby.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
        player.setLobby(this);
    }

    /**
     * Removes a player from the lobby.
     *
     * @param player The player to remove.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        //make game engine
        gameEngine = new GameEngine(board, new NormalVariantController(board, maxPlayers), -1);
        // make bots to the desired number of players
        int toAdd = maxPlayers - getPlayerCount();
        List<BotPlayer> bots = new ArrayList<>();
        for (int i = 0; i < toAdd; i++) {
            BotPlayer bot = null;
            if(i < 2) {
                 bot = new BotPlayer(System.out, gameEngine, new DeepDistanceBotStrategy());
            } else {
                 bot = new BotPlayer(System.out, gameEngine, new DistanceBotStrategy());
            }
            addPlayer(bot);
            bots.add(bot);
//            Scanner in = new Scanner(socket.getInputStream());
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            while (in.hasNextLine()) {
//                clientInputHandler.exec(in.nextLine());
//            }
        }

        // zaczynamy gre zeby GameEngine ustawil pionki itp
        gameEngine.startGame();



        if (board.correctPlayerCount(this.getPlayerCount())) {
            inGame = true;
//            notifyAll("Started game");
            // players must know their numbers
            turnController = new TurnController(players);
            Collection<com.dawid.game.Player> playerNumbers = gameEngine.getPlayers();
            Iterator<com.dawid.game.Player> iterator = playerNumbers.iterator();
            for (Player player : players) {
                com.dawid.game.Player gamePlayer = iterator.next();
                player.setNumber(gamePlayer.getHomeField());
                player.setWinFieldID(gamePlayer.getWinField());
                if(variant.getVariant() == Variant.ORDER_CHAOS) {
                    player.setNumber(gamePlayer.getWinField());
                    player.setWinFieldID(gamePlayer.getHomeField());
                }
                player.sendMessage("Started " + player.getNumber() + " " + getPlayerCount() + " " + getVariant());
            }
            for(BotPlayer botPlayer : bots) {
                botPlayer.setupBoard(board);
            }

            variant.initializeGame(this);

            turnController.getCurrrentPlayer().makeTurn();
        } else {
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
     *
     * @param player The player making the move.
     * @param args   The arguments of the move.
     */
    public void makeMove(Player player, String[] args) {
        if (!inGame) {
            player.sendMessage("ERROR: Game not started");
        }
        if (!player.equals(turnController.getCurrrentPlayer())) {
            player.sendMessage("ERROR: Not your turn");
            return;
        }
        player.getLobby().notifyAll("Moved: Player " + player.getNumber() + " " + String.join(" ", args));
        //for now, we assume that this won't fail
        movePawnOnBoard(player.getNumber(), Coordinates.fromString(args[1]), Coordinates.fromString(args[2]));
        turnController.nextTurn();
        turnController.getCurrrentPlayer().makeTurn();
    }

    public void forceMove(Player player, String[] args) {
        player.getLobby().notifyAll("Moved: Player " + player.getNumber() + " " + String.join(" ", args));
        //for now, we assume that this won't fail
        movePawnOnBoard(player.getNumber(), Coordinates.fromString(args[1]), Coordinates.fromString(args[2]));
    }

    public Player getPlayer(int number) {
        for (Player player : players) {
            if (player.getNumber() == number) {
                return player;
            }
        }
        return null;
    }

    private void movePawnOnBoard(int pawn, Coordinates from, Coordinates to) {
        gameEngine.makeMoveFromServer(pawn, from.getRow(), from.getColumn(), to.getRow(), to.getColumn());
//
//        board.getField(from.getRow(), from.getColumn()).setPawn(0);
//        board.getField(to.getRow(), to.getColumn()).setPawn(pawn);
        board.printBoard();
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
     *
     * @param variant The variant of the game.
     */
    public void setVariant(Variant variant) {
        this.variant = Variant.getGameVariant(variant);
    }

    /**
     * Set the maximum number of players in the lobby.
     *
     * @param maxPlayers The maximum number of players in the lobby.
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }
}
