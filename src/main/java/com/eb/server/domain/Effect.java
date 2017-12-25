package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Effect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    Attribute attribute;
    Long value;
    @ManyToOne
    Card card;
}
