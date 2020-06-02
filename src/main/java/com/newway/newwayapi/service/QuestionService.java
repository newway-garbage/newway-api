package com.newway.newwayapi.service;

import com.newway.newwayapi.repository.AnswerRepository;
import com.newway.newwayapi.repository.QuestionRepository;
import com.newway.newwayapi.service.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private VoteService voteService;
    private CommentService commentService;
    private AnswerService answerService;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository,
                           VoteService voteService, CommentService commentService, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.voteService = voteService;
        this.commentService = commentService;
        this.answerService = answerService;
    }

    public Page<QuestionDto> readQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable).map(q -> {
            QuestionDto dto = new QuestionDto();
            dto.setId(q.getId());
            dto.setTitle(q.getTitle());
            dto.setQuestion(q.getQuestion());
            dto.setTags(q.getTags());
            dto.setDeveloper(q.getDeveloper());
            dto.setAnswerNumber(answerRepository.countByQuestion(q));
            dto.setVoteNumber(voteService.countByQuestion(q));
            return dto;
        });
    }

    public Optional<QuestionDto> readQuestion(Long id) {
        return questionRepository.findById(id).map(q -> {
            QuestionDto dto = new QuestionDto();
            dto.setId(q.getId());
            dto.setTitle(q.getTitle());
            dto.setQuestion(q.getQuestion());
            dto.setTags(q.getTags());
            dto.setDeveloper(q.getDeveloper());
            dto.setCommentDto(commentService.readCommentsByQuestion(q));
            dto.setAnswers(answerService.readAnswerByQuestion(q));
            dto.setAnswerNumber(answerRepository.countByQuestion(q));
            dto.setVoteNumber(voteService.countByQuestion(q));
            return dto;
        });
    }
}
