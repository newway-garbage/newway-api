package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Question;
import com.newway.newwayapi.repository.QuestionRepository;
import com.newway.newwayapi.service.QuestionService;
import com.newway.newwayapi.service.dto.QuestionDto;
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
@RequestMapping("v1/questions")
public class QuestionResource {

    private final QuestionRepository questionRepository;

    private final QuestionService questionService;

    public QuestionResource(QuestionRepository questionRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) throws URISyntaxException {
        if (question.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Question p = questionRepository.save(question);
        return ResponseEntity.created(new URI("v1/questions/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> readQuestions(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<QuestionDto> page = questionService.readQuestions(pageable);
        uriBuilder.path("v1/questions");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDto> readQuestion(@PathVariable Long id) {
        Optional<QuestionDto> question = questionService.readQuestion(id);
        return question.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        if (question.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Question p = questionRepository.save(question);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
