package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.repository.DeveloperRepository;
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

import static com.newway.newwayapi.utils.PaginationUtil.generatePaginationHttpHeaders;

@RestController
@RequestMapping("v1/developers")
public class DeveloperResource {

    private final DeveloperRepository developerRepository;

    public DeveloperResource(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) throws URISyntaxException {
        if (developer.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Developer p = developerRepository.save(developer);
        return ResponseEntity.created(new URI("v1/developers/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Developer>> readDevelopers(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<Developer> page = developerRepository.findAll(pageable);
        uriBuilder.path("v1/developers");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Developer> readDeveloper(@PathVariable Long id) {
        Optional<Developer> developer = developerRepository.findById(id);
        return developer.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Developer> updateDeveloper(@RequestBody Developer developer) {
        if (developer.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Developer p = developerRepository.save(developer);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        developerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
