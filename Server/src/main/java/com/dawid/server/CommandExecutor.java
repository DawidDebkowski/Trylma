package com.dawid.server;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor implements CommandHandler {
    private Map<String, Command> commands;
    private Player player;
    public CommandExecutor(Player player) {
        this.player = player;
        commands = new HashMap<>();
        //TODO: add commands and link them to methods
    }
    @Override
    public void exec(String command) {
        String[] args = command.split(" ");
        if(commands.containsKey(args[0])) {
            commands.get(args[0]).exec(args);
        }
        else {
            player.sendMessage("Unknown command");
        }
    }
    public void joinGame(String[] args) {
        Lobby lobby = GamesManager.getInstance().getLobby(Integer.parseInt(args[1]));
        if(lobby == null) {
            player.sendMessage("Lobby does not exist");
        }
        else {
            lobby.addPlayer(player);
            player.sendMessage("Joined lobby " + args[1]);
        }

    }
    public void createLobby(String[] args) {
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        GamesManager.getInstance().addLobby(lobby);
    }

}
