package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class GamePhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private GamePhaseType gamePhaseType;

//    private Object payload;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gamePhase")
    private List<GameCommand> gameCommands;

    @OneToOne
    private Game game;
}
