package com.dawid.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity(name = "GameInformation")
@Table(name = "games")
public class GameInformation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<Move> moves;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<PlayerEntity> players;

    public Long getId() {
        return id;
    }
}
