package com.newway.newwayapi.web.rest;


import com.newway.newwayapi.model.Devloper;
import com.newway.newwayapi.model.Project;
import com.newway.newwayapi.repository.DevloperRepository;
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
@RequestMapping("v1/devlopers")
public class DevloperController {
    @Autowired
    DevloperRepository devloperRepository;

    @GetMapping("{id}")
    public ResponseEntity<Devloper> getById(@PathVariable Long id) {
        Optional<Devloper> devget = devloperRepository.findById(id);
        return devget.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
     @GetMapping
    public ResponseEntity<List<Devloper>> readDevloper(Pageable pageable, @RequestParam MultiValueMap<String,
            String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<Devloper> page = devloperRepository.findAll(pageable);
        uriBuilder.path("v1/devlopers/");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }
    @PostMapping
    public ResponseEntity<Devloper> createDevloper(@RequestBody Devloper devloper) throws URISyntaxException {
        if (devloper.getId() != null) {
            throw new BadRequest("Already have an ID");
        }

        Devloper p = devloperRepository.save(devloper);
        return ResponseEntity.created(new URI("v1/devlopers/" + p.getId())).body(p);
    }

    @PutMapping("{id}")
    public ResponseEntity<Devloper> updateDevloper(@RequestBody Devloper devloper) {
        if (devloper.getId() == null) {
            throw new BadRequest("Invalid ID: Null");
        }
        Devloper result = devloperRepository.save(devloper);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDevloper(@PathVariable Long id) {
        devloperRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}