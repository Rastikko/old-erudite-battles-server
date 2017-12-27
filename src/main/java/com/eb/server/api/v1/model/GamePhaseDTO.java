package com.eb.server.api.v1.model;

import com.eb.server.domain.GamePhaseType;
import lombok.Data;

import java.util.List;

@Data
public class GamePhaseDTO {
    private Long id;
    private GamePhaseType gamePhaseType;
    private List<GameCommandDTO> gameComands;
//    private String payload;
}
