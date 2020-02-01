package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Developer;
import com.newway.newwayapi.repository.DeveloperRepository;
import com.newway.newwayapi.web.rest.errors.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.newway.newwayapi.util.PaginationUtil.generatePaginationHttpHeaders;

@RestController
@RequestMapping("v1/developers")
public class DeveloperController {

    @Autowired
    private DeveloperRepository developerRepository;

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) throws URISyntaxException {
        if (developer.getId() != null) {
            throw new RuntimeException("Already have an ID");
        }

        Developer p = developerRepository.save(developer);
        return ResponseEntity.created(new URI("v1/developers/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Developer>> readDevelopers(Pageable pageable, @RequestParam MultiValueMap<String,
            String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<Developer> page = developerRepository.findAll(pageable);
        uriBuilder.path("v1/developers/");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Developer> readDeveloper(@PathVariable Long id) {
        Optional<Developer> todo = developerRepository.findById(id);
        return todo.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<Developer> updateDeveloper(@RequestBody Developer developer) {
        if (developer.getId() == null) {
            throw new BadRequest("Invalid ID: Null");
        }
        Developer result = developerRepository.save(developer);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        developerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
