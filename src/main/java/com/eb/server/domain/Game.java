package com.eb.server.domain;

import com.eb.server.api.v1.model.GamePlayerDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GamePlayer> gamePlayers;
}
