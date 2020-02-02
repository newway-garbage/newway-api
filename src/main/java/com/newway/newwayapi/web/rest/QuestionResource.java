package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Question;
import com.newway.newwayapi.repository.QuestionRepository;
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
@RequestMapping("api/v1/questions")
public class QuestionResource {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) throws URISyntaxException {
        if (question.getId() != null) {
            throw new RuntimeException("Already have an ID");
        }

        Question q = questionRepository.save(question);
        return ResponseEntity.created(new URI("v1/questions/" + q.getId())).body(q);
    }

    @GetMapping
    public ResponseEntity<List<Question>> readQuestions(Pageable pageable, @RequestParam MultiValueMap<String,
            String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<Question> page = questionRepository.findAll(pageable);
        uriBuilder.path("v1/questions/");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> readQuestion(@PathVariable Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        if (question.getId() == null) {
            throw new BadRequest("Invalid ID: Null");
        }
        Question result = questionRepository.save(question);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
