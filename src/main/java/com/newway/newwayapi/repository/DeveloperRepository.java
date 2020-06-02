package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findOneByUsername(String username);

    Optional<Developer> findOneByEmail(String username);

    Optional<Developer> findOneWithAuthoritiesByUsername(String username);

    Optional<Developer> findOneWithAuthoritiesByEmailIgnoreCase(String email);

}
