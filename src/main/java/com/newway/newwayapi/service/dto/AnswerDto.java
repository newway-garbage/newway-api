package com.newway.newwayapi.service.dto;

import com.newway.newwayapi.entity.Developer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AnswerDto {
    private String answer;
    private Developer developer;
    private Long voteCount;
    private CommentDto commentDto;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public void setCommentDto(CommentDto commentDto) {
        this.commentDto = commentDto;
    }

    public String getAnswer() {
        return answer;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public CommentDto getCommentDto() {
        return commentDto;
    }
}
