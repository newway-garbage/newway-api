package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"developer_id", "answer_id"}),
        @UniqueConstraint(columnNames = {"developer_id", "comment_id"}),
        @UniqueConstraint(columnNames = {"developer_id", "project_id"}),
        @UniqueConstraint(columnNames = {"developer_id", "question_id"})
})
public class Vote extends AbstractEntity {

    @Column(nullable = false)
    private boolean up;
    @ManyToOne
    private Developer developer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;


}
