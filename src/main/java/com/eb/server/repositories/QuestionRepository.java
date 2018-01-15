package com.eb.server.repositories;

import com.eb.server.domain.Question;
import com.eb.server.domain.types.QuestionAffinityType;
import com.eb.server.domain.types.QuestionCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryAndAffinityAndIdNotIn(QuestionCategoryType categoryType, QuestionAffinityType affinityType, List<Long> ids);
}
