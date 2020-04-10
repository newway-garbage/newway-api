package com.newway.newwayapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Developer extends AbstractEntity {

    @Column(nullable = false, unique = true, length = 34)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Boolean activated = false;
    private String activationKey;
}
