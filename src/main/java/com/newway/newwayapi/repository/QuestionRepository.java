package com.newway.newwayapi.repository;

import com.newway.newwayapi.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    Long countQuestionByDeveloper_Id(Long id);

    Question findTopicById(Long id);

    List<Question> findQuestionByDeveloper_IdOrderByCreatedDate(Long id);

}
