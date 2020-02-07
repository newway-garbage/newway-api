package com.newway.newwayapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Devloper extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
}
