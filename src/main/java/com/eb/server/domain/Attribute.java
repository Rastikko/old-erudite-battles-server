package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    AttributeType attributeType;
    Long value;
    @ManyToOne
    Card card;
}
