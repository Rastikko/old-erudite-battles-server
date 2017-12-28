package com.eb.server.api.v1.model;

import com.eb.server.domain.GamePlayer;
import lombok.Data;

import java.util.List;

@Data
public class GamePlayerDTO {
    private Long id;
    private Long userId;
    private List<GameCardDTO> deck;
    private List<GameCardDTO> hand;
}
