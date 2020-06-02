package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.service.DeveloperService;
import com.newway.newwayapi.service.dto.LoginDTO;
import com.newway.newwayapi.service.dto.RegisterDTO;
import com.newway.newwayapi.web.rest.errors.AccountResourceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static com.newway.newwayapi.config.Constants.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("auth")
public class AuthenticationResource {

    private DeveloperService developerService;

    public AuthenticationResource(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("register")
    public ResponseEntity<Developer> registerDeveloper(@RequestBody RegisterDTO registerDTO) throws URISyntaxException, RuntimeException {
        Developer developer = developerService.registerDeveloper(registerDTO);
        return ResponseEntity.created(new URI("v1/developers/" + developer.getId())).body(developer);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> loginDeveloper(@RequestBody LoginDTO loginDTO) {
        String token = developerService.login(loginDTO.getLogin(), loginDTO.getPassword(), loginDTO.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + token);
        return new ResponseEntity<>(new JWTToken(token), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Developer> getMe() {
        return developerService.getUserWithAuthorities().
                map(developer -> ResponseEntity.ok().body(developer))
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }


    @AllArgsConstructor
    static class JWTToken {

        @Getter
        @Setter
        private String token;
    }
}
