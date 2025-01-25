package com.dawid.services;
import com.dawid.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveGameService {
    private GameRepository gameRepository;

    @Autowired
    public SaveGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


}
