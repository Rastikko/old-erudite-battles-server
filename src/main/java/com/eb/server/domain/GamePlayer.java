package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;
    private Long userId;

//    private List<Card> deck;
//    private List<Card> hand;
}
