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
                final CommandHandler clientInputHandler = CommandHandler.create(new Player(socket.getOutputStream()));
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                    clientInputHandler.exec(in.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Error:" + socket);
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
