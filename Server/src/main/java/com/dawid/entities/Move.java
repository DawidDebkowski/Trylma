package com.dawid.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Move")
@Table(name = "moves")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    @Column(name = "moveCommand")
    private String move;

    @ManyToOne
    private GameInformation gameInformation;



    public Move(String move) {
        this.move = move;
    }

}
