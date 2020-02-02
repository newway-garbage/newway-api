package com.newway.newwayapi.model;

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
public class Comment extends AbstractEntity {

    @Column(nullable = false)
    private String comment;
    @ManyToOne
    private Developer developer;
    @OneToMany
    private List<Vote> votes;
}
