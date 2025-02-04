package com.dawid;

import com.dawid.game.GamesManager;
import com.dawid.game.Lobby;
import com.dawid.game.Variant;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Executes commands sent by the player.
 */
public class CommandExecutor implements CommandHandler {
    private Map<String, Command> commands;
    private final Player player;
    public CommandExecutor(Player player) {
        this.player = player;
        commands = new HashMap<>();
        commands.put("MOVE", this::move);
        commands.put("CREATE", this::createLobby);
        commands.put("JOIN", this::joinGame);
        commands.put("LEAVE", this::leaveLobby);
        commands.put("START", this::startGame);
        commands.put("LOBBYINFO", this::sendLobbyInfo);
        commands.put("VARIANT", this::setVariant);
        commands.put("MAX_PLAYERS", this::setMaxPlayers);
        commands.put("SAVE", this::saveGame);
        commands.put("LOAD", this::loadSaved);
    }


    @Override
    public void exec(String command) {
        String[] args = command.split(" ");
        System.out.println(command);
        if(commands.containsKey(args[0])) {
            try {
                commands.get(args[0]).exec(args);
            } catch (IllegalArgumentException e) {
                player.sendMessage("ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            player.sendMessage("ERROR: Unknown command");
        }
    }

    private void sendLobbyInfo(String[] strings) {
        System.out.println("Sending lobby info");
        Collection<Lobby> lobbies = GamesManager.getInstance().getLobbies();
        player.sendMessage("Lobbies:");
        for (Lobby lobby : lobbies) {
            //Format "Number players variant"
            player.sendMessage(GamesManager.getInstance().getLobbyId(lobby) + " " + lobby.getPlayerCount() + " " + lobby.getVariant().getName()
                    + " " + lobby.getMaxPlayers());
            System.out.println(GamesManager.getInstance().getLobbyId(lobby) + " " + lobby.getPlayerCount() + " " + lobby.getVariant().getName()
                    + " " + lobby.getMaxPlayers());
        }
        player.sendMessage("END");
        System.out.println("End of lobbies");
    }

    private void joinGame(String[] args) throws IllegalArgumentException {
        Lobby lobby = GamesManager.getInstance().getLobby(Integer.parseInt(args[1]));
        if(lobby == null) {
            throw new IllegalArgumentException("Lobby does not exist");
        }
        else {
            lobby.addPlayer(player);
            player.sendMessage("Joined lobby " + args[1]);
        }

    }
    private void createLobby(String[] args) {
        System.out.println("Create command");
        Lobby lobby;
        if(args.length == 1) {
            lobby = new Lobby(Variant.NORMAL);
        }
        else {
            lobby = new Lobby(Variant.getVariantByName(args[1]));
        }
        lobby.addPlayer(player);
        GamesManager.getInstance().addLobby(lobby);
        player.sendMessage("Created lobby " + GamesManager.getInstance().getLobbyId(lobby));
    }
    private void move(String[] args) {
        System.out.println("Move command in lobby " + player.getLobby());
        player.getLobby().makeMove(player, args);

    }
    private void leaveLobby(String[] args) {
        System.out.printf("Player %d left lobby\n", player.getNumber());
        player.getLobby().removePlayer(player);
        player.sendMessage("Left lobby");
    }
    private void startGame(String[] args) {
        System.out.println("Some game has started");
        synchronized (player.getLobby()) {
            player.getLobby().startGame();
        }


    }
    private void setVariant(String[] args) {
        System.out.println("Setting variant");
        player.getLobby().setVariant(Variant.getVariantByName(args[1]));
    }
    private void setMaxPlayers(String[] args) {
        System.out.println("Setting max players");
        player.getLobby().setMaxPlayers(Integer.parseInt(args[1]));
    }

    private void loadSaved(String[] args) {
        System.out.println("Loading saved game with id " + args[1]);
        Lobby lobby  = GamesManager.getInstance().restoreGame(Long.parseLong(args[1]));
        if(lobby == null) {
            player.sendMessage("ERROR: Game not found");
            return;
        }
        lobby.addPlayer(player);
        player.sendMessage("Loaded: " + args[1]);

    }

    private void saveGame(String[] args) {
        System.out.println("Saving game");
        Long id = GamesManager.getInstance().saveGame(player.getLobby());
        player.sendMessage("Saved: " + id);
    }

}
