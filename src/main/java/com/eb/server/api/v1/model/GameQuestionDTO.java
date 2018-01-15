package com.eb.server.api.v1.model;

import lombok.Data;

import java.util.Calendar;

@Data
public class GameQuestionDTO {
    private Long id;

    private Integer turn;
    private Calendar startDate;
    private Calendar endDate;
    private String selectedAnswer;
    private Integer performance;

    private QuestionDTO question;
}
