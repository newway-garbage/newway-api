package com.newway.newwayapi.service;

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

    @Autowired
    private AnswerService answerService;

    public Page<QuestionDto> readQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable).map(q -> {
            QuestionDto dto = new QuestionDto();
            dto.setTitle(q.getTitle());
            dto.setQuestion(q.getQuestion());
            dto.setTags(q.getTags());
            dto.setDeveloper(q.getDeveloper());
            dto.setAnswerCount(answerRepository.countByQuestion(q));
            dto.setVoteCount(voteRepository.countByQuestion(q));
            return dto;
        });
    }

    public Optional<QuestionDto> readQuestion(Long id) {
        return questionRepository.findById(id).map(q -> {
            QuestionDto dto = new QuestionDto();
            dto.setTitle(q.getTitle());
            dto.setQuestion(q.getQuestion());
            dto.setTags(q.getTags());
            dto.setDeveloper(q.getDeveloper());
            dto.setAnswers(answerService.readAnswerByQuestion(q));
            dto.setCommentDto(commentService.readCommentsByQuestion(q));
            dto.setAnswerCount(answerRepository.countByQuestion(q));
            dto.setVoteCount(voteRepository.countByQuestion(q));
            return dto;
        });
    }

}
