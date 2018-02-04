package com.eb.server.domain;

import com.eb.server.domain.types.GameType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private GameType gameType = GameType.VS_BOT;

    private Integer turn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GamePlayer> gamePlayers;

    @OneToOne(cascade = CascadeType.ALL)
    private GamePhase gamePhase;

}
