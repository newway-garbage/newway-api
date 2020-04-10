package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Answer extends AbstractEntity {

    @Column(nullable = false)
    private String answer;
    @ManyToOne
    private Developer developer;
    @ManyToOne
    private Question question;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Vote> votes;


}
