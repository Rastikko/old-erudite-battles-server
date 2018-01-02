package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: make this dynamic
    @Enumerated(value = EnumType.STRING)
    private GameType gameType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GamePlayer> gamePlayers;

    @OneToOne(cascade = CascadeType.ALL)
    private GamePhase gamePhase;
}
