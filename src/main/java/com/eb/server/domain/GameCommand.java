package com.eb.server.domain;

import com.eb.server.domain.types.GameCommandType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GameCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private GameCommandType type;

    private Long userId;
    // TODO: make sure payload is always a json object
    private String payload;
}
