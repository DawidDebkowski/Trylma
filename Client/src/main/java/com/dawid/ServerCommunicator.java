package com.dawid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//TODO: test this class when the server is online
public class ServerCommunicator{
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    ServerCommunicator(String serverAdress, int port) throws IOException {
        socket = new Socket(serverAdress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /*
        Możnaby tutaj słuchać serwera. Zaimplementować obserwatora w CLI, żeby robił coś tylko, jak tutaj się coś stanie.
        Chociaż tak naprawdę to ta klasa chciałby coś robić z CLI.
        -> przy otrzymaniu ruchu chciałby powiedzieć CLI, że teraz jego ruch
        -> przy otrzymaniu potwierdzenia klasy State będa zmieniać stany klienta
     */

    public boolean move(String from, String to) {
        out.println("MOVE" + " " + from + " " + to);
        awaitResponse();
        return true;
    }

    public boolean join(int lobbyID) {
        out.println("JOIN" + " " + lobbyID);
        return true;
    }

    public void leaveLobby() {
        out.println("LEAVE");
    }

    public boolean startGame() {
        out.println("START");
        return true;
    }

    public boolean create() {
        out.println("CREATE");
        awaitResponse();
        return true;
    }

    private String awaitResponse() {
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            System.out.println("zerwane połączenie");
        }
        System.out.println(response);
        return response;
    }
}
