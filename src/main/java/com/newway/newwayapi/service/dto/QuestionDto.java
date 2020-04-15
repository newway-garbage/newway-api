package com.newway.newwayapi.service.dto;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class QuestionDto {

    private Long id;
    private String title;
    private String question;
    private Developer developer;
    private List<Tag> tags;
    private CommentDto commentDto;
    private Long answerCount;
    private Long voteCount;
    private List<AnswerDto> answers;
}
