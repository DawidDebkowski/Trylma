package com.dawid.services;
import com.dawid.entities.GameInformation;
import com.dawid.game.Lobby;
import com.dawid.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

   public void saveGame(Lobby lobby) {
        //TODO: Implement
   }

}
