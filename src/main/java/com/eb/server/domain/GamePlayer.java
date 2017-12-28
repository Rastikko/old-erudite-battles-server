package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deckGamePlayer")
    private List<GameCard> deck;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "handGamePlayer")
    private List<GameCard> hand;

    @ManyToOne
    private Game game;

    private Long userId;
}
