package com.eb.server.api.v1.model;

import com.eb.server.domain.types.QuestionAffinityType;
import com.eb.server.domain.types.QuestionCategoryType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String correctAnswer;
    private List<String> potentialAnswers = new ArrayList<>();
    private QuestionCategoryType category;
    private QuestionAffinityType affinity;
    private Integer averageAnswerTime;
}
