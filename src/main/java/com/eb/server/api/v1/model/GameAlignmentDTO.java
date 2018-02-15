package com.eb.server.api.v1.model;

import lombok.Data;

@Data
public class GameAlignmentDTO {
    private Long id;

    private Integer logicAlignment = 0;
    private Integer cultureAlignment = 0;
    private Integer scienceAlignment = 0;
}