package com.newway.newwayapi.service;

import com.newway.newwayapi.repository.AnswerRepository;
import com.newway.newwayapi.repository.QuestionRepository;
import com.newway.newwayapi.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            dto.setTitle(q.getTitle());
            dto.setQuestion(q.getQuestion());
            dto.setDeveloper(q.getDeveloper());
            dto.setTags(q.getTags());
            dto.setCommentDto(commentService.readCommentsByQuestion(q));
            dto.setAnswerCount(answerRepository.countByQuestion(q));
            dto.setVoteCount(voteRepository.countByQuestion(q));
            return dto;
        });
    }
}
