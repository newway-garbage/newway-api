package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Comment;
import com.newway.newwayapi.repository.CommentRepository;
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
@RequestMapping("v1/comments")
public class CommentResource {

    private final CommentRepository commentRepository;

    public CommentResource(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws URISyntaxException {
        if (comment.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Comment p = commentRepository.save(comment);
        return ResponseEntity.created(new URI("v1/comments/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> readComments(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<Comment> page = commentRepository.findAll(pageable);
        uriBuilder.path("v1/comments");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> readComment(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        if (comment.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Comment p = commentRepository.save(comment);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
