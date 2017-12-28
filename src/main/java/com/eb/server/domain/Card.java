package com.eb.server.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "deck")
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    private List<Attribute> attributes;
}
