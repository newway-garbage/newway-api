package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Vote;
import com.newway.newwayapi.repository.VoteRepository;
import com.newway.newwayapi.web.rest.errors.BadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.newway.newwayapi.util.PaginationUtil.generatePaginationHttpHeaders;

@RestController
@RequestMapping("v1/votes")
public class VoteResource {

    private final VoteRepository voteRepository;

    public VoteResource(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) throws URISyntaxException {
        if (vote.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Vote p = voteRepository.save(vote);
        return ResponseEntity.created(new URI("v1/votes/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Vote>> readVotes(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<Vote> page = voteRepository.findAll(pageable);
        uriBuilder.path("v1/votes");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Vote> readVote(@PathVariable Long id) {
        Optional<Vote> vote = voteRepository.findById(id);
        return vote.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Vote> updateVote(@RequestBody Vote vote) {
        if (vote.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Vote p = voteRepository.save(vote);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        voteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
