package com.eb.server.api.v1.model;

import com.eb.server.domain.GameQuestion;
import lombok.Data;

import java.util.List;

@Data
public class GamePlayerDTO {
    private Long id;
    private Long userId;
    private Integer energy;
    private Integer attack;
    private Integer health;
    // TODO: deck just need to expose size
    private List<GameCardDTO> deck;
    private List<GameCardDTO> hand;
    private List<GameCardDTO> permanents;
    private GameQuestionDTO currentGameQuestion;
    private List<GameQuestionDTO> gameQuestions;
}
