package com.dawid.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Player")
@Table(name = "players")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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


    // Can be added

}
