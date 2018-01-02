package com.eb.server.api.v1.model;

import lombok.Data;

@Data
public class RequestGameCommandDTO {

    private String gameCommandType;
    private Long userId;
    private String payload;

}
