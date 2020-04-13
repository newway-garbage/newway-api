package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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
    @JoinColumn(nullable = false)
    private Developer developer;
    @ManyToMany
    @JoinTable(name = "question_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "tags_id"}))
    private List<Tag> tags;

}