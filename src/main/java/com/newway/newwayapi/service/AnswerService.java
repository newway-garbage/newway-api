package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.repository.AnswerRepository;
import com.newway.newwayapi.repository.VoteRepository;
import com.newway.newwayapi.service.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CommentService commentService;

    public List<AnswerDto> readAnswerByQuestion(Question question) {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdDate"));
        return answerRepository.findAllByQuestion(question, pageable).map(a -> {
            AnswerDto dto = new AnswerDto();
            dto.setAnswer(a.getAnswer());
            dto.setDeveloper(a.getDeveloper());
            dto.setVoteCount(voteRepository.countByAnswer(a));
            dto.setCommentDto(commentService.readCommentsByAnswer(a));
            return dto;
        }).getContent();
    }
}
