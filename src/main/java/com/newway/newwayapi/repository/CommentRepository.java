package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Comment;
import com.newway.newwayapi.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByQuestion(Question question);

    Page<Comment> findAllByQuestion(Question question, Pageable pageable);

    Long countByAnswer(Answer answer);

    Page<Comment> findAllByAnswer(Answer answer, Pageable pageable);
}
