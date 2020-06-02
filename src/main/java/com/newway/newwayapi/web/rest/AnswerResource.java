package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Answer;
import com.newway.newwayapi.repository.AnswerRepository;
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
@RequestMapping("v1/answers")
public class AnswerResource {

    private final AnswerRepository answerRepository;

    public AnswerResource(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) throws URISyntaxException {
        if (answer.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Answer p = answerRepository.save(answer);
        return ResponseEntity.created(new URI("v1/answers/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Answer>> readAnswers(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<Answer> page = answerRepository.findAll(pageable);
        uriBuilder.path("v1/answers");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Answer> readAnswer(@PathVariable Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Answer> updateAnswer(@RequestBody Answer answer) {
        if (answer.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Answer p = answerRepository.save(answer);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}