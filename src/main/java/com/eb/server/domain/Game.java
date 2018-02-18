package com.eb.server.domain;

import com.eb.server.domain.interfaces.Attributer;
import com.eb.server.domain.types.GameType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Game extends Attributer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private GameType gameType = GameType.VS_BOT;

    private Integer turn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GamePlayer> gamePlayers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GamePhase> gamePhases = new ArrayList<>();

    public void playCard(Long gamePlayerId, Long cardId) {
//        setAttribute(this.getGamePlayerByUserId(gamePlayerId, ));
    }

    public GameCard getGameCard(Long gamePlayerId, Long cardId) {
        return null;
    }

    public GamePlayer getGamePlayerByUserId(Long userId) {
        return this.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getUserId() == userId)
                .findFirst()
                .get();
    }

    public GamePlayer getOtherGamePlayerByUserId(Long userId) {
        return this.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getUserId() != userId)
                .findFirst()
                .get();
    }

    public GamePlayer getOtherGamePlayerByGamePlayerId(Long gamePlayerId) {
        return this.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getId() != gamePlayerId)
                .findFirst()
                .get();
    }

    public GamePhase getGamePhase() {
        if (this.gamePhases.size() > 0) {
            return this.gamePhases.get(this.gamePhases.size() - 1);
        }
        return null;
    }

    public GamePhase getPreviousGamePhase() {
        if (this.gamePhases.size() > 1) {
            return this.gamePhases.get(this.gamePhases.size() - 2);
        }
        return null;
    }

}
