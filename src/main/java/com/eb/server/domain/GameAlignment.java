package com.eb.server.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class GameAlignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Integer logicAlignment = 0;
    Integer cultureAlignment = 0;
    Integer scienceAlignment = 0;

    Integer logicLevel = 0;
    Integer cultureLevel = 0;
    Integer scienceLevel = 0;
}
