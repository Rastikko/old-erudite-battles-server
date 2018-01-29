package com.eb.server.domain;

import com.eb.server.domain.types.QuestionSubcategoryType;
import com.eb.server.domain.types.QuestionCategoryType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String correctAnswer;

    @ElementCollection
    private List<String> potentialAnswers = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private QuestionCategoryType category;

    @Enumerated(value = EnumType.STRING)
    private QuestionSubcategoryType subCategory;
    private Integer averageAnswerTime;
}

// gamePlayer will have a question that MIGHT be null
// gamePlayer will have a bonus modifier starting from 0,
// depending on the performance of the question it will recieve some points from 0 to 1.
// after 30 seconds the player will not be able to answer any more questions