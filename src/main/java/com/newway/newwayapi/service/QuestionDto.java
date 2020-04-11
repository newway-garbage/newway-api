package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.entity.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {

    private String title;
    private String question;
    private Developer developer;
    private List<Tag> tags = new ArrayList<>();
    private CommentDto commentDto;
    private Long answerCount;
    private Long voteCount;
}
