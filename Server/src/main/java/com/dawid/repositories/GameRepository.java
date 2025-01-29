package com.dawid.repositories;

import com.dawid.entities.GameInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameInformation, Long> {}
