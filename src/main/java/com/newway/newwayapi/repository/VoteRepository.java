package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Long countByQuestion(Question question);

    Long countByAnswer(Answer answer);

    Long countByComment(Comment comment);

    Long countByProject(Project project);

}
