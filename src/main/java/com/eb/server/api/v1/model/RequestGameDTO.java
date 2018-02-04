package com.eb.server.api.v1.model;

import com.eb.server.domain.types.GameType;
import lombok.Data;

@Data
public class RequestGameDTO {
    private Long userId;
    private GameType type = GameType.VS_BOT;
}
