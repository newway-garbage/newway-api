package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Comment;
import com.newway.newwayapi.repository.CommentRepository;
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
@RequestMapping("api/v1/comments")
public class CommentResource {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws URISyntaxException {
        if (comment.getId() != null) {
            throw new RuntimeException("Already have an ID");
        }

        Comment c = commentRepository.save(comment);
        return ResponseEntity.created(new URI("v1/comments/" + c.getId())).body(c);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> readComments(Pageable pageable, @RequestParam MultiValueMap<String,
            String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<Comment> page = commentRepository.findAll(pageable);
        uriBuilder.path("v1/comments/");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> readComment(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        if (comment.getId() == null) {
            throw new BadRequest("Invalid ID: Null");
        }
        Comment result = commentRepository.save(comment);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
