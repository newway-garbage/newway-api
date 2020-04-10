package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractEntity {

    @Column(nullable = false)
    private String comment;
    @ManyToOne
    private Developer developer;
    @ManyToOne
    private Question question;
    @ManyToOne
    private Answer answer;
}