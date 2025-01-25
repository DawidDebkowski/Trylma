package com.dawid.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Move")
@Table(name = "moves")
@Builder
public class Move implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @Column(name = "moveCommand")
    private String move;

    @ManyToOne
    private GameInformation gameInformation;

    public Move() {
    }

    public Move(String move) {
        this.move = move;
    }

}
