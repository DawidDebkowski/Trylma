package com.dawid.server;


import jakarta.persistence.*;

@Entity(name = "Move")
@Table(name = "move")
public class Move implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    @Column(name = "moveCommand")
    private String move;

    @ManyToOne
    private GameInformation gameInformation;

    public Move() {
    }

    public Move(String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
