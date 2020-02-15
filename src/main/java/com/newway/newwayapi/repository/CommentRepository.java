package com.newway.newwayapi.repository;

import com.newway.newwayapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    void deleteCommentById(Long id);

    Long countCommentByDeveloper_Id(Long id);

    List<Comment> findCommentByDeveloper_IdOrOrderByCreatedDate(Long id);


}
