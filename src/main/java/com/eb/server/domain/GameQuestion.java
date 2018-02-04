package com.eb.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Entity
public class GameQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer turn;
    private Calendar startDate;
    private Calendar endDate;
    private String selectedAnswer;
    private Integer performance;

    @OneToOne(cascade = CascadeType.ALL)
    private Question question;
}
