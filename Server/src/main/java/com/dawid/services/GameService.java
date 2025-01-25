package com.dawid.services;
import com.dawid.entities.GameInformation;
import com.dawid.entities.Move;
import com.dawid.entities.PlayerEntity;
import com.dawid.game.BotPlayer;
import com.dawid.game.Lobby;
import com.dawid.game.Player;
import com.dawid.game.SavedLobby;
import com.dawid.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

   public Long saveGame(Lobby lobby) {
        List<Move> moves = new ArrayList<>();
        for (String move : lobby.getMoves()) {
            moves.add(new Move(move));
        }

        List<PlayerEntity> players = new ArrayList<>();
        for (Player player : lobby.getPlayers()) {
            players.add(PlayerEntity.builder().
                    numberOnBoard(player.getNumber()).
                    isBot(player instanceof BotPlayer).
                    build());
        }

        GameInformation gameInformation = GameInformation.builder().
                variant(lobby.getVariant()).
                moves(moves).
                players(players).
                currentPlayer(lobby.getCurrentPlayerNumber()).
                build();

        gameRepository.save(gameInformation);
        return gameInformation.getId();
   }
   public Lobby getLobby(Long id) {
        GameInformation info = gameRepository.findById(id).orElse(null);
        if (info == null) {
            return null;
        }
        return new SavedLobby(info);
   }

}
