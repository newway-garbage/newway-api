package com.newway.newwayapi.repository;

import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByQuestion(Question question);
}
