package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.entity.Vote;
import com.newway.newwayapi.repository.AnswerRepository;
import com.newway.newwayapi.repository.QuestionRepository;
import com.newway.newwayapi.repository.VoteRepository;
import com.newway.newwayapi.service.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CommentService commentService;

    public Page<QuestionDto> readQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable).map(q -> {
            QuestionDto dto = new QuestionDto();
            dto.setId(q.getId());
            dto.setTitle(q.getTitle());
            dto.setQuestion(q.getQuestion());
            dto.setTags(q.getTags());
            dto.setDeveloper(q.getDeveloper());
            dto.setCommentDto(commentService.readCommentsByQuestion(q));
            dto.setAnswerNumber(answerRepository.countByQuestion(q));
            dto.setVoteNumber(voteRepository.countByQuestion(q));
            return dto;
        });
    }

}
