package com.dawid.services;

import com.dawid.entities.GameInformation;
import com.dawid.game.BotPlayer;
import com.dawid.game.Lobby;
import com.dawid.game.Player;
import com.dawid.game.Variant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameServiceIT {

    @Autowired
    private GameService gameService;
    private static Lobby lobby;

    @BeforeAll
    public static void setUp() {
        lobby = new Lobby(Variant.NORMAL);
        lobby.addPlayer(mock(Player.class));
        lobby.addPlayer(mock(BotPlayer.class));
    }

    @Test
    public void serviceShouldNotBeNull() {
        assertNotNull(gameService);
    }
    @Test
    public void shouldSaveGame() {
        Long id = gameService.saveGame(lobby);
        assertNotNull(id);
    }

    @Test
    public void gameShouldHavePlayers() {
        Long id = gameService.saveGame(lobby);
        GameInformation retrievedLobby = gameService.getGame(id);
        List<Player> players = lobby.getPlayers();
        assertEquals(lobby.getPlayerCount(), players.size());

    }

}
