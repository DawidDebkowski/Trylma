package com.dawid.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A server program which accepts requests from clients to capitalize strings.
 * When a client connects, a new thread is started to handle it. Receiving
 * client data, capitalizing it, and sending the response back is all done on
 * the thread, allowing much greater throughput because more clients can be
 * handled concurrently.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        final int port = 5005;
        int maxThreads;
        try {
            maxThreads = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("Usage: java Server <maxThreads>");
            System.out.println("(Correct) argument not given, using default values.");
            maxThreads = 50;
        }
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Chinese checkers server is running on port " + port);
            try (ExecutorService pool = Executors.newFixedThreadPool(maxThreads)) {
                while (true) {
                    pool.execute(new Capitalizer(listener.accept()));
                }
            }
        }
    }

    private static class Capitalizer implements Runnable {
        private final Socket socket;
        private final joinGameHandler clientInputHandler;

        Capitalizer(Socket socket) {
            this.socket = socket;
            this.clientInputHandler = joinGameHandler.create(socket);
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine() && !clientInputHandler.joinedGame()) {
                    String response = clientInputHandler.exec(in.nextLine());
                    if(response != null) {
                        out.println(response);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try {
                    if(!clientInputHandler.joinedGame()) {
                        socket.close();
                        System.out.println("Closed: " + socket);
                    }
                } catch (IOException e) {
                }
            }
        }
    }
}
