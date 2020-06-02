package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Answer extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_seq")
    @SequenceGenerator(name = "answer_id_seq")
    private Long id;

    @Column(nullable = false)
    private String answer;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Developer developer;
    @ManyToOne
    private Question question;


}
