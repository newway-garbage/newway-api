package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Developer;
import com.newway.newwayapi.repository.DeveloperRepository;
import com.newway.newwayapi.service.DeveloperService;
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
@RequestMapping("api/v1/developers")
public class DeveloperResource {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private DeveloperService developerService;

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) throws URISyntaxException {
        Developer d = developerService.createDeveloper(developer);
        return ResponseEntity.created(new URI("v1/developers/" + d.getId())).body(d);
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
        Optional<Developer> developer = developerRepository.findById(id);
        return developer.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<Developer> updateDeveloper(@RequestBody Developer developer) {
        if (developer.getId() == null) {
            throw new BadRequest("Invalid ID: Null");
        }
        Developer d = developerRepository.save(developer);
        return ResponseEntity.ok().body(d);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        developerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
