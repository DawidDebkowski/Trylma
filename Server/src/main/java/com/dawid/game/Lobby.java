package com.dawid.game;

import com.dawid.Player;
import lombok.Getter;
import lombok.Setter;
import com.dawid.bot.BotPlayer;
import com.dawid.bot.DistanceBotStrategy;
import com.dawid.bot.DeepDistanceBotStrategy;

import java.util.*;

/**
 * Represents a lobby.
 */
public class Lobby {
    // We could use state pattern, but it's not necessary if only 2 states are possible
    protected boolean inGame = false;
    /**
     * -- GETTER --
     *  Returns the board of the game.
     */
    @Getter
    protected final Board board;
    protected TurnController turnController;

    @Getter
    protected final List<Player> players;
    @Getter
    @Setter
    protected int maxPlayers;

    protected GameEngine gameEngine;

    protected final List<String> moveHistory = new ArrayList<>();
    protected GameVariant variant;
    public Lobby(List<Player> players, Variant variant) {
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
    public Lobby() {
        this.players = new ArrayList<>();
        //only DavidStarBoard is implemented
        board = new DavidStarBoard();
        this.variant = Variant.getGameVariant(Variant.NORMAL);
    }
    /**
     * Notifies all players in the lobby.
     * @param message The message to send.
     */
    public void notifyAll(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
        moveHistory.add(message);
    }
    public void notifyAllNoHistory(String message) {
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

    protected void assignPlayerNumbers() {
        Collection<Integer> playerNumbers = board.getPlayerNumbers(this.getPlayerCount());
        Iterator<Integer> iterator = playerNumbers.iterator();
        players.forEach(player -> player.setNumber(iterator.next()));
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
            if (i < 2) {
                bot = new BotPlayer(System.out, gameEngine, new DeepDistanceBotStrategy());
            } else {
                bot = new BotPlayer(System.out, gameEngine, new DistanceBotStrategy());
            }
            addPlayer(bot);
            bots.add(bot);
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
                if (variant.getVariant() == Variant.ORDER_CHAOS) {
                    player.setNumber(gamePlayer.getWinField());
                    player.setWinFieldID(gamePlayer.getHomeField());
                }
            }
            for (BotPlayer botPlayer : bots) {
                botPlayer.setupBoard(board);
            }
            //TODO: modify to use this method
            // assignPlayerNumbers();
            players.forEach(player -> player.sendMessage("Started " + player.getNumber() + " " + getPlayerCount() + " " + getVariant()));
            variant.initializeGame(this);

            turnController.getCurrrentPlayer().makeTurn();
            GamesManager.getInstance().removeLobby(this);
        } else {
            notifyAll("ERROR: Incorrect number of players");
        }
        for (String move : moveHistory) {
            String[] args = move.split(" ");
            args[0] = args[3];
            args[1] = args[4];
            args[2] = args[5];
            System.out.println(move);
            forceMoveNoHistory(getPlayer(Integer.parseInt(move.split(" ")[2])), args);
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
    public void forceMoveNoHistory(Player player, String[] args) {
        player.getLobby().notifyAllNoHistory("Moved: Player " + player.getNumber() + " " + String.join(" ", args));
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
     * Sets the variant of the game.
     * @param variant The variant of the game.
     */
    public void setVariant(Variant variant) {
        this.variant = Variant.getGameVariant(variant);
    }

    public List<String> getMoves() {
        return moveHistory;
    }

    public Integer getNumberOfHumanPlayers() {
        int pl = 0;
        for(Player player : players) {
            if(!(player instanceof BotPlayer)) {
                pl++;
            }
        }
        return pl;
    }

    public int getCurrentPlayerNumber() {
        return turnController.getCurrrentPlayer().getNumber();
    }
}
