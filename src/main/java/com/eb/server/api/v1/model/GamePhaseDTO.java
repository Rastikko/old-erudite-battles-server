package com.eb.server.api.v1.model;

import com.eb.server.domain.types.GamePhaseType;
import lombok.Data;

@Data
public class GamePhaseDTO {
    private Long id;
    private GamePhaseType type;
}
