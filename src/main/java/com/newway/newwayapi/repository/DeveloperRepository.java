package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
