package com.dawid.server;

import com.dawid.game.GameInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends JpaRepository<GameInformation, Long> {}
