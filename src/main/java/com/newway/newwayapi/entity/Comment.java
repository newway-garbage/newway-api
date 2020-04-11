package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"developer_id", "answer_id"}),
        @UniqueConstraint(columnNames = {"developer_id", "question_id"})
})
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