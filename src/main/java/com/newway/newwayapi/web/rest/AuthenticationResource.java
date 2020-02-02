package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.model.Developer;
import com.newway.newwayapi.security.jwt.TokenProvider;
import com.newway.newwayapi.service.DeveloperService;
import com.newway.newwayapi.service.dto.AuthenticationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static com.newway.newwayapi.config.Constants.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("api/auth")
public class AuthenticationResource {

    @Autowired
    DeveloperService developerService;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    TokenProvider tokenProvider;

    @PostMapping("register")
    public ResponseEntity<Developer> registerDeveloper(@RequestBody AuthenticationDTO authenticationDTO) throws URISyntaxException {
        Developer developer = developerService.registerDeveloper(authenticationDTO);
        return ResponseEntity.created(new URI("v1/developers/" + developer.getId())).body(developer);
    }

    @PostMapping("login")
    public ResponseEntity<JWTToken> loginDeveloper(@RequestBody AuthenticationDTO authenticationDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication, authenticationDTO.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + token);
        return new ResponseEntity<>(new JWTToken(token), httpHeaders, HttpStatus.OK);
    }


    @AllArgsConstructor
    static class JWTToken {

        @Getter
        @Setter
        private String token;
    }
}
