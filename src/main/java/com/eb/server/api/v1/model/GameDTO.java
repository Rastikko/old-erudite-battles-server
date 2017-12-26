package com.eb.server.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class GameDTO {
    private Long id;
    private List<GamePlayerDTO> gamePlayers;
}
