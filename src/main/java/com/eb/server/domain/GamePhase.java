package com.eb.server.domain;

import com.eb.server.domain.types.GamePhaseType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GamePhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    // TODO: change to type
    private GamePhaseType type;
}
