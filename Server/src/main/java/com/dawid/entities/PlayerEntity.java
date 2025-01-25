package com.dawid.entities;


import jakarta.persistence.*;
import lombok.Builder;

@Entity(name = "Player")
@Table(name = "players")
@Builder
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    // Can be added
//    @Column(name = "name")
//    private String name;

    @Column(name = "is_bot")
    private boolean isBot;


    @Column(name="number")
    private int numberOnBoard;

    @ManyToOne
    private GameInformation gameInformation;


    public PlayerEntity() {
    }

    public Long getId() {
        return id;
    }


    // Can be added

}
