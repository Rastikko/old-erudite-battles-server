package com.eb.server.domain;

import com.eb.server.domain.types.AttributeType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    AttributeType type;
    Integer value;
}
