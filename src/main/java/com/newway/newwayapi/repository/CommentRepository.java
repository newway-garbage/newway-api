package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Comment;
import com.newway.newwayapi.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByQuestion(Question question);
}
