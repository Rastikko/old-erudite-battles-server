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
    private Integer deck;
    private List<GameCardDTO> hand;
    private List<GameCardDTO> permanents;
    // TODO: we need to filter the answer for this question
    private GameQuestionDTO currentGameQuestion;
    private List<GameQuestionDTO> gameQuestions;
}
