package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GameCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    GamePlayer deckGamePlayer;

    @ManyToOne
    GamePlayer handGamePlayer;
}
