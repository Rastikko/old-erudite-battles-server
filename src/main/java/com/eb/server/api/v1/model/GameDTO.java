package com.eb.server.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class GameDTO {
    private Long id;
    private Integer turn;
    private List<GamePlayerDTO> gamePlayers;
    private GamePhaseDTO gamePhase;
}
