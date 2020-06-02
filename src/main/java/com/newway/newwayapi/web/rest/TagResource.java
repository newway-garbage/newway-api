package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Tag;
import com.newway.newwayapi.repository.TagRepository;
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
@RequestMapping("v1/tags")
public class TagResource {

    private final TagRepository tagRepository;

    public TagResource(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) throws URISyntaxException {
        if (tag.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Tag p = tagRepository.save(tag);
        return ResponseEntity.created(new URI("v1/tags/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> readTags(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<Tag> page = tagRepository.findAll(pageable);
        uriBuilder.path("v1/tags");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Tag> readTag(@PathVariable Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag) {
        if (tag.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Tag p = tagRepository.save(tag);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
