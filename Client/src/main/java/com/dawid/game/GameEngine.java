package com.dawid.game;

import com.dawid.IClient;
import com.dawid.gui.ClientGUI;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class will control how the game is played.
 * It combines a board and a variant to make a game.
 */
public class GameEngine {
    /**
     * ID of player that is using this controller
    */
    int playerID;
    // List of currently playing playerIDs
    List<Player> players;
    // Board type
    Board board;
    // Game Variant
    IVariantController variantController;
    IClient client;
    List<Function<Integer, String>> listeners;

    ArrayList<Player> winners = new ArrayList<>();

    boolean isMyTurn = false;

    public GameEngine(Board board, IVariantController variantController, int playerNumber, IClient client) {
        this.board = board;
        this.variantController = variantController;
        this.playerID = playerNumber;
        this.client = client;
        listeners = new ArrayList<>();
    }

    public void startGame() {
        players = variantController.setupPlayers();
        variantController.setupPawns(players);
        board.printBoard();
    }

    /**
     *  If player owning this GameController can move.
     */
    public boolean isYourTurn() {
        return isMyTurn;
    }

    /**
     * Sets turn
     * @param myTurn new value
     */
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    /**
     * It will just (without checking anything) make that move.
     * -1 -1 means turn skip.
     * The board Fields must exist.
     * Checks for win
     */
    public void makeMoveFromServer(int player, int sx, int sy, int fx, int fy)  {
        if(isSkipMove(new Coordinates(sx, sy), new Coordinates(fx, fy))) {
            System.out.println("[GameEngine]:" + player + " skipped move");
            return;
        }
        variantController.movePawn(player, sx, sy, fx, fy);

        Player winner = variantController.checkWin();
        if(winner != null) {
            if(winners.contains(winner)) { return;}
            winners.add(winner);
            System.out.println("[GameEngine]:" + winner.id + " won");
            for(Function<Integer, String> listener : listeners) {
                listener.apply(winner.id);
            }
        }
    }

    public void addListener(Function<Integer, String> listener) {
        listeners.add(listener);
    }

    /**
     * Sends a move request to the server if the move is valid.
     * Validates beforehand.
     * @param from start
     * @param to end
     */
    public void sendMoveToServer(Coordinates from, Coordinates to) {
        if(!isYourTurn()) {return;}
        if(!isSkipMove(from, to)) {
            if(!tryMove(from, to)) {return;}
        }
        // if it is my turn AND (it is a skip move OR (it isn't a skip move AND it is valid))
        setMyTurn(false);
        client.getServerCommands().move(from.getRow(), from.getColumn(), to.getRow(), to.getColumn());
    }

    public int getMyPlayerID() {
        return playerID;
    }

    private boolean isSkipMove(Coordinates from, Coordinates to) {
        return from.getRow() == -1 && to.getRow() == -1;
    }

    /**
     * Validate local moves before sending them to the server.
     * It should be called when a player selects a place to move their pawn to.
     * It shouldn't be used to move the pawn - only the server's response should do that.
     * @param from start
     * @param to end
     * @return is this move possible
     */
    public boolean tryMove(Coordinates from, Coordinates to) {
        if(!isYourTurn()) return false;
        return isMovePossible(from, to);
    }

    /**
     * Function for the GUI to know if the move can be made regardless of turn order
     * @param from start
     * @param to end
     * @return is this move possible
     */
    public boolean isMovePossible(Coordinates from, Coordinates to) {
        Field startField = board.getField(from.getRow(), from.getColumn());
        Field finishField = board.getField(to.getRow(), to.getColumn());
        return variantController.getPossibleMoves(startField).contains(finishField);
    }

    /**
     * Returns a collection of all the possible moves from a given field.
     * @param c start coordinates
     * @return collection of possible move coordinates
     */
    public Collection<Coordinates> getPossibleMoves(Coordinates c) {
        //if you won you can't move
        boolean didWin = false;
        for(Player player : winners) {
            if(player.id == playerID) {
                didWin = true;
                break;
            }
        }
        if(didWin) {return null;}

        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = variantController.getPossibleMoves(field);
        Collection<Coordinates> possibleMovesList = new ArrayList<>();
        for(Field possibleMove : possibleMoves) {
            possibleMovesList.add(board.getCoordinates(possibleMove));
        }
        return possibleMovesList;
    }

    /**
     * For testing. Marks all possible moves with a 9-pawn.
     */
    public void showPossibleMoves(Coordinates c) {
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = variantController.getPossibleMoves(field);
        for (Field f : possibleMoves) {
            f.setPawn(9);
        }
    }

    // ludzki test getPossibleMoves()
    public static void main(String[] args) {
        DavidStarBoard board = new DavidStarBoard();
        GameEngine gameEngine = new GameEngine(board, new NormalVariantController(board, 6), 0, new ClientGUI());
        gameEngine.startGame();
        board.debugPrint();
        board.getField(6, 10).setPawn(7);
        board.getField(6, 12).setPawn(7);
        board.getField(6, 16).setPawn(7);
        board.getField(7, 9).setPawn(7);
        board.getField(9, 7).setPawn(7);
        board.printBoard();
        gameEngine.showPossibleMoves(new Coordinates(6, 10));
        board.printBoard();
    }

    public Board getBoard() {
        return board;
    }
    public boolean isMyPawn(int pawn) {
        return pawn == playerID;
    }
}
