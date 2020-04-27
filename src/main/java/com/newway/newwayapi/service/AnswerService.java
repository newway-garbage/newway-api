package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.repository.AnswerRepository;
import com.newway.newwayapi.service.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private CommentService commentService;

    @Autowired
    private VoteService voteService;

    public List<AnswerDto> readAnswerByQuestion(Question question) {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdDate"));
        Page<Answer> page = answerRepository.findAllByQuestion(question, pageable);

        return page.map(a -> {
            AnswerDto dto = new AnswerDto();
            dto.setId(a.getId());
            dto.setAnswer(a.getAnswer());
            dto.setDeveloper(a.getDeveloper());
            dto.setCommentDto(commentService.readCommentsByAnswer(a));
            dto.setVoteCount(voteService.countByAnswer(a));
            return dto;
        }).getContent();
    }

}
