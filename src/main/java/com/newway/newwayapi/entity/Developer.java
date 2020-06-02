package com.newway.newwayapi.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_id_seq")
    @SequenceGenerator(name = "developer_id_seq")
    private Long id;

    @Column(nullable = false, unique = true, length = 34)
    private String username;
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
