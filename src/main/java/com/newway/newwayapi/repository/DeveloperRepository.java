package com.newway.newwayapi.repository;

import com.newway.newwayapi.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Developer getDeveloperByUsername(String username);
    Developer getDeveloperById(long Id);

    List<Developer> findAll();



}
