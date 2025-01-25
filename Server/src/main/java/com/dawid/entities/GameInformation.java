package com.dawid.entities;


import com.dawid.game.Variant;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "GameInformation")
@Table(name = "games")
@Builder
public class GameInformation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<Move> moves = new ArrayList<>();

    @Column(name = "variant")
    private Variant variant;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<PlayerEntity> players;

    public GameInformation() {

    }
}
