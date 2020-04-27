package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Long countByQuestion(Question question) {
        long upCount = voteRepository.countByQuestionAndUp(question, true);
        long downCount = voteRepository.countByQuestionAndUp(question, false);
        return upCount - downCount;
    }


    public Long countByAnswer(Answer answer) {
        long upCount = voteRepository.countByAnswerAndUp(answer, true);
        long downCount = voteRepository.countByAnswerAndUp(answer, false);
        return upCount - downCount;
    }
}
