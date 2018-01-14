package com.eb.server.api.v1.model;

import com.eb.server.domain.types.AffinityType;
import com.eb.server.domain.types.QuestionCategoryType;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
    private Long id;
    private String title;
    private String correctAnswer;
    private List<String> potentialAnswers = new ArrayList<>();
    private QuestionCategoryType category;
    private AffinityType affinity;
}
