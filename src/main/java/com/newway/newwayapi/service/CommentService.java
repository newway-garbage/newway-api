package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Comment;
import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentDto readCommentsByQuestion(Question q) {
        Page<Comment> page = commentRepository.findAllByQuestion(q, getPageable());
        CommentDto dto = new CommentDto();
        dto.setComments(page.getContent());
        dto.setCommentCount(commentRepository.countByQuestion(q));
        return dto;
    }

    public CommentDto readCommentsByAnswer(Answer a) {
        Page<Comment> page = commentRepository.findAllByAnswer(a, getPageable());
        CommentDto dto = new CommentDto();
        dto.setComments(page.getContent());
        dto.setCommentCount(commentRepository.countByAnswer(a));
        return dto;
    }

    private PageRequest getPageable() {
        return PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdDate"));
    }
}
