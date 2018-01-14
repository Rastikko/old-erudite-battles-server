package com.eb.server.api.v1.model;

import lombok.Data;

// TODO: change to gameCommand
@Data
public class GameCommandDTO {

    private String type;
    private Long userId;
    private String payload;

}
