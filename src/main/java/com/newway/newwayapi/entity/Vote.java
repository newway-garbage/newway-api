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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_seq")
    @SequenceGenerator(name = "vote_id_seq")
    private Long id;

    @Column(nullable = false)
    private boolean up;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Developer developer;
    @ManyToOne
    private Answer answer;
    @ManyToOne
    private Comment comment;
    @ManyToOne
    private Project project;
    @ManyToOne
    private Question question;

}
