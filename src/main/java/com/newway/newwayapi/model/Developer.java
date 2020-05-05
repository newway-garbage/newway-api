package com.newway.newwayapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Developer extends AbstractEntity {

    @Column(nullable = false, length = 55, unique = true)
    private String username;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Boolean activated = false;
    private String activationKey;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "developer_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @BatchSize(size = 20)
    Set<Authority> authorities = new HashSet<>();
}
