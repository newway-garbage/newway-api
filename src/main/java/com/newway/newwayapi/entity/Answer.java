package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Answer extends AbstractEntity {

    @Column(nullable = false)
    private String answer;
    @ManyToOne
    private Developer developer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

}
