package com.eb.server.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class GameCardDTO {
    private Long id;
    private String name;
    private Integer cost;
    private List<AttributeDTO> attributes;
}
