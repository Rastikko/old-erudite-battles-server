package com.eb.server.domain;

import com.eb.server.domain.types.GamePhaseType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class GamePhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCommand> gameCommands = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private GamePhaseType type;

    @ElementCollection
    private List<Long> endPhaseGamePlayerIds = new ArrayList<>();

    private String payload;
}
