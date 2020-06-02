package com.newway.newwayapi.service.dto;

import com.newway.newwayapi.entity.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class CommentDto {

    List<Comment> comments = new ArrayList<>();
    Long commentCount;
}