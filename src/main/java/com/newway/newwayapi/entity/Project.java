package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends AbstractEntity {

    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @ManyToOne
    private Developer developer;

}
