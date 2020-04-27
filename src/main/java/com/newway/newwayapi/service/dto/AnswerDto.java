package com.newway.newwayapi.service.dto;

import com.newway.newwayapi.entity.Developer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AnswerDto {
    private Long id;
    private String answer;
    private Developer developer;
    private Long voteCount;
    private CommentDto commentDto;
}