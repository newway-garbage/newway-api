package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Comment;
import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.repository.CommentRepository;
import com.newway.newwayapi.service.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto readCommentsByQuestion(Question question) {
        Page<Comment> page = commentRepository.findAllByQuestion(question, getPageable());
        CommentDto dto = new CommentDto();
        dto.setComments(page.getContent());
        dto.setCommentCount(page.getTotalElements());
        return dto;
    }

    public CommentDto readCommentsByAnswer(Answer answer) {
        Page<Comment> page = commentRepository.findAllByAnswer(answer, getPageable());
        CommentDto dto = new CommentDto();
        dto.setComments(page.getContent());
        dto.setCommentCount(page.getTotalElements());
        return dto;
    }

    private Pageable getPageable() {
        return PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdDate"));
    }
}
