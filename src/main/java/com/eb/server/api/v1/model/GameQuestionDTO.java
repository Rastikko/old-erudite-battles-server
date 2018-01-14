package com.eb.server.api.v1.model;

import com.eb.server.domain.Question;

import java.util.Date;

public class GameQuestionDTO {
    private Long id;

    private Integer turn;
    private Date startDate;
    private Date endDate;
    private String selectedAnswer;
    private Integer performance;

    private Question question;
}
