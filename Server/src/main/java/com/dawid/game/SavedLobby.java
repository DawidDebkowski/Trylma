package com.dawid.game;

import com.dawid.CommandHandler;
import com.dawid.Player;
import com.dawid.entities.GameInformation;
import com.dawid.entities.Move;
import com.dawid.entities.PlayerEntity;
import java.util.List;

/**
 * A lobby that was saved and can be restored.
 * The start method is overriden, so that it also restores moves from history.
 * @see Lobby
 */
public class SavedLobby extends Lobby {
    private final int currentPlayer;

    public SavedLobby(GameInformation info) {
        super(info.getVariant());
        currentPlayer = info.getCurrentPlayer();
        List<PlayerEntity> players = info.getPlayers();
        for (PlayerEntity player : players) {
            if (player.isBot()) {
                BotPlayer bot = new BotPlayer(System.out);
                CommandHandler.create(bot);
                this.addPlayer(bot);
            }
        }
        for (Move move : info.getMoves()) {
            moveHistory.add(move.getMove());
        }
    }
    @Override
    public void startGame() {
        // make bots to the desired number of players
        for(int i=0;i<maxPlayers-getPlayerCount();i++) {
            BotPlayer bot = new BotPlayer(System.out);
            CommandHandler.create(bot);
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
            turnController = new TurnController(players, currentPlayer);
            //TODO: we should think of a way to assign the players the numbers they had before
            // dk how, probably before connecting they should choose their position
            // or somehow save ip or sth else?
            assignPlayerNumbers();
            for(Player player : players) {
                player.sendMessage("Started " + player.getNumber() + " " + getPlayerCount() + " " + getVariant());
            }
            variant.initializeGame(this);
            GamesManager.getInstance().removeLobby(this);
            for(String move : moveHistory) {
                notifyAllNoHistory(move);
            }
            turnController.getCurrrentPlayer().sendMessage("TURN");
        }
        else {
            notifyAll("ERROR: Incorrect number of players");
        }
    }
}
