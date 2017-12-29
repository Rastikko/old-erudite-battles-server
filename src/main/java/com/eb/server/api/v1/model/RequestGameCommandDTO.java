package com.eb.server.api.v1.model;

import com.eb.server.domain.GameCommandType;
import com.eb.server.domain.GamePhase;
import lombok.Data;

import javax.persistence.*;

@Data
public class RequestGameCommandDTO {

    private GameCommandType gameCommandType;
    private Long userId;
    private String payload;

}
