package com.dawid.server;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor implements CommandHandler {
    private Map<String, Command> commands;
    private Player player;
    public CommandExecutor(Player player) {
        this.player = player;
        commands = new HashMap<>();
        commands.put("MOVE", this::move);
        commands.put("CREATE", this::createLobby);
        commands.put("JOIN", this::joinGame);
        commands.put("LEAVE", this::leaveLobby);
        commands.put("START", this::startGame);
        //TODO: add commands and link them to methods
    }
    @Override
    public void exec(String command) {
        String[] args = command.split(" ");
        if(commands.containsKey(args[0])) {
            try {
                commands.get(args[0]).exec(args);
            } catch (IllegalArgumentException e) {
                player.sendMessage(e.getMessage());
            }
        }
        else {
            player.sendMessage("Unknown command");
        }
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
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        GamesManager.getInstance().addLobby(lobby);
        player.sendMessage("Created lobby " + GamesManager.getInstance().getLobbyId(lobby));
    }
    private void move(String[] args) {
        System.out.println("Move command");
        // Probably we will need to add logic here in the future
        player.getLobby().notifyAll("VALID_MOVE " + player.getNumber() + " " + String.join(" ", args));
    }
    private void leaveLobby(String[] args) {
        System.out.printf("Player %d left lobby\n", player.getNumber());
        player.getLobby().removePlayer(player);
        player.sendMessage("Left lobby");
    }
    private void startGame(String[] args) {
        System.out.println("Some game has started");
        player.getLobby().startGame();
        player.getLobby().notifyAll("Started game");
    }

}
