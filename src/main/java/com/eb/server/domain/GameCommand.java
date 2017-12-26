package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GameCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private GameCommandType gameCommandType;

    @ManyToOne
    private GamePhase gamePhase;
}
