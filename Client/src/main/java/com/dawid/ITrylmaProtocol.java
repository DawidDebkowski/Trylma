package com.dawid;

/**
 * Class implementing a basic protocol to communicate with the server;
 */

/**
 * Według mnie trzymamy mapę połączeń i jakiegoś rodzaju id klientów.
 * Na bazie tej mapy wysyłamy wszystkim wiadomości o ruchu.
 *
 * Client -> Server
 * MOVE <String> <String> - sends a move
 *
 *
 * Server -> Client
 * VALID - confirms that the action is OK
 * INVALID - action is invalid, pretty much an error message
 */
public interface ITrylmaProtocol {
    boolean move(String from, String to);
}
