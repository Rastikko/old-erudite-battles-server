package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class GameCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer cost;

    @OneToMany
    private List<Attribute> attributes;


}
