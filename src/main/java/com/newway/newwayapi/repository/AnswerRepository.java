package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Long countByQuestion(Question question);

    Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
