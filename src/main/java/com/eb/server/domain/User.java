package com.eb.server.domain;

import com.eb.server.domain.types.UserStateType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long gameId;

    @Enumerated(value = EnumType.STRING)
    private UserStateType state;

    @ManyToMany
    @JoinTable(name = "user_card",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> deck = new ArrayList<>();
}
