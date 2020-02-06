package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Authority;
import com.newway.newwayapi.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.newway.newwayapi.util.PaginationUtil.generatePaginationHttpHeaders;

@RestController
@RequestMapping("api/v1/authorities")
public class AuthorityResource {

    @Autowired
    private AuthorityRepository authorityRepository;

    @PostMapping
    public ResponseEntity<Authority> createAuthority(@RequestBody Authority authority) throws URISyntaxException {

        Authority t = authorityRepository.save(authority);
        return ResponseEntity.created(new URI("v1/authorities/" + t.getName())).body(t);
    }

    @GetMapping
    public ResponseEntity<List<Authority>> readAuthorities(Pageable pageable, @RequestParam MultiValueMap<String,
            String> queryParams, UriComponentsBuilder uriBuilder) {
        Page<Authority> page = authorityRepository.findAll(pageable);
        uriBuilder.path("v1/authorities/");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
