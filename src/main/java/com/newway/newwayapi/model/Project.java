package com.newway.newwayapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @OneToMany
    private List<Developer> developers = new ArrayList<>();
    @OneToMany
    @Column(nullable = false)
    private List<Tag> tags;

}
