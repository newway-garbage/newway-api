package com.newway.newwayapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Vote extends AbstractEntity {

    @Column(nullable = false)
    private boolean up;
    @ManyToOne
    @Column(nullable = false)
    private Developer developer;
    @ManyToOne
    private Question question;
    @ManyToOne
    private Comment comment;

}
