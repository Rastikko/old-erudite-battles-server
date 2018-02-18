package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer energy = 0;
    private Integer attack = 0;
    private Integer health = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCard> deck = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCard> hand = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameCard> cemetery = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private GameQuestion currentGameQuestion;

    @OneToOne(cascade = CascadeType.ALL)
    private GameAlignment gameAlignment = new GameAlignment();

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameQuestion> gameQuestions = new ArrayList<>();

    private Long userId;

    private Boolean isBot = false;

    GameCard getHandGameCard(Long gameCardId) {
        return this.getHand().stream()
                .filter(gameCard -> gameCard.getId().equals(gameCardId))
                .findFirst()
                .get();
    }

    public void discardHandGameCard(Long gameCardId) {
        GameCard gameCard = getHandGameCard(gameCardId);
        this.hand.remove(gameCard);
        this.cemetery.add(gameCard);
    }
}
