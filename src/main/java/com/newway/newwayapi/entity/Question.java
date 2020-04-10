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
public class Question extends AbstractEntity {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String question;
    @ManyToOne
    private Developer developer;
    @OneToMany
    private List<Tag> tags;
    @OneToMany
    private List<Answer> answers;
    @OneToMany
    private List<Vote> votes;

}