package com.dawid.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    boolean isMyTurn = false;

    public GameEngine(Board board, IVariantController variantController, int playerNumber) {
        this.board = board;
        this.variantController = variantController;
        this.playerID = playerNumber;
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
            System.out.println("[GameEngine]:" + player + " won");
        }
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
        Field field = board.getField(c.getRow(), c.getColumn());
        Collection<Field> possibleMoves = variantController.getPossibleMoves(field);
        Collection<Coordinates> possibleMovesList = new ArrayList<>();
        for(Field possibleMove : possibleMoves) {
            possibleMovesList.add(board.getCoordinates(possibleMove));
        }
        return possibleMovesList;
    }

    public Board getBoard() {
        return board;
    }
    public boolean isMyPawn(int pawn) {
        return pawn == playerID;
    }
}
