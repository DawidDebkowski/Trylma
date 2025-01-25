package com.dawid.server;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "GameInformation")
@Table(name = "game")
public class GameInformation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<Move> moves;

    public Long getId() {
        return id;
    }
}
