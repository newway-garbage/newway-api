package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractEntity {

    @Column(nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Developer developer;
    @ManyToOne
    private Question question;
    @ManyToOne
    private Answer answer;
}