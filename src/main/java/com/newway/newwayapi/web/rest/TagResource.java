package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Tag;
import com.newway.newwayapi.repository.TagRepository;
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
@RequestMapping("v1/tags")
public class TagResource {

    @Autowired
    private TagRepository tagRepository;

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) throws URISyntaxException {
        if (tag.getId() != null) {
            throw new RuntimeException("Already have an ID");
        }

        Tag t = tagRepository.save(tag);
        return ResponseEntity.created(new URI("v1/tags/" + t.getId())).body(t);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> readTags(Pageable pageable, @RequestParam MultiValueMap<String,
            String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<Tag> page = tagRepository.findAll(pageable);
        uriBuilder.path("v1/tags/");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Tag> readTag(@PathVariable Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag) {
        if (tag.getId() == null) {
            throw new BadRequest("Invalid ID: Null");
        }
        Tag result = tagRepository.save(tag);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
