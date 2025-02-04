package com.dawid;

import com.dawid.game.DavidStarBoard;
import com.dawid.game.GamesManager;
import com.dawid.services.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server for the Chinese checkers game.
 * The server listens for incoming connections and creates a new thread for each client.
 * The server can be run with a specified number of threads.
 * The server uses the CommandHandler to handle client commands.
 * The server uses the Player class to send messages to clients.
 * The server uses the Lobby class to manage the game state.
 */
@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Server.class, args);
        GameService gameService = context.getBean(GameService.class);
        GamesManager.getInstance().setGameService(gameService);
        DavidStarBoard board = new DavidStarBoard();
        final int port = 5005;
        int maxThreads;
        try {
            maxThreads = Integer.parseInt(args[0]);
        } catch (Exception e) {
            maxThreads = 50;
            System.out.println("The server will use the default number of threads: " +  maxThreads);
        }
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Chinese checkers server is running on port " + port);
            ExecutorService pool = Executors.newFixedThreadPool(maxThreads);
                while (true) {
                    pool.execute(new Capitalizer(listener.accept()));
                }
            }
        catch (IOException e) {
            System.out.println("Error starting the server");
        }
        }

    private static class Capitalizer implements Runnable {
        private final Socket socket;
        Capitalizer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                final Player player = new Player(socket.getOutputStream());
                final CommandHandler clientInputHandler = CommandHandler.create(player);
                Scanner in = new Scanner(socket.getInputStream());
                player.sendMessage("Connected to " + socket.getInetAddress().getHostAddress());
                while (in.hasNextLine()) {
                    clientInputHandler.exec(in.nextLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("Error:" + socket);
            } finally {
                try {
                    socket.close();
                    System.out.println("Closed: " + socket);
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + socket);
                }
            }
        }
    }
}
