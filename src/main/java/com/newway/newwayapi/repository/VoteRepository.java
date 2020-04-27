package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByQuestion(Question question);

    Long countByQuestion(Question question);

    Long countByQuestionAndUp(Question question, boolean up);

    Optional<Vote> findByDeveloperAndQuestion(Developer developer, Question question);

    Long countByAnswerAndUp(Answer answer, boolean up);
}
