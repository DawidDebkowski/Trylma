package com.dawid.cli;

public enum Commands {
    help(
      "h", "help",
      "Displays all commands.", 0
    ),
    move("m", "move",
            "Usage: move [start] [end]. Moves your piece from start to end.", 2
    ),
    showBoard("show", "showBoard",
            "Displays the current state of the board.", 0
    ),
    exit("exit", "exit",
            "Exits the program.", 0
    ),
    disconnect("dc", "disconnect",
            "Disconnects the current connection.", 0
    ),
    join("j", "join",
            "Joins a lobby with the given ID", 1
    ),
    leave("l", "leave",
            "Leaves the lobby.", 0
    ),
    start("s", "start",
            "Starts the game", 0
    ),
    create("cr", "create",
            "Create a new game.", 0
    ),
    ;

    private final String shortcut;
    private final String fullName;
    private final String description;
    private final int minArgs;

    Commands(String shortcut, String fullName, String desc, int minArgs) {
        this.shortcut = shortcut;
        this.fullName = fullName;
        this.description = desc;
        this.minArgs = minArgs;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortcut() {
        return shortcut;
    }

    public String getDescription() {
        return description;
    }

    public int minArgs() {
        return minArgs;
    }

    private boolean compare(String in) {
        in = in.toLowerCase();
        return in.compareTo(this.shortcut) == 0 || in.compareTo(this.fullName.toLowerCase()) == 0;
    }

    public static Commands stringToCommand(String in) {
        for (Commands command : Commands.values()) {
            if(command.compare(in)) {
                return command;
            }
        }
        return null;
    }
}
