package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer energy = 0;
    private Integer attack;
    private Integer health;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCard> deck = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCard> hand = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCard> permanents = new ArrayList<>();

    private Long userId;
}
