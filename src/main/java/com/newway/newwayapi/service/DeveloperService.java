package com.newway.newwayapi.service;

import com.newway.newwayapi.model.Authority;
import com.newway.newwayapi.model.Developer;
import com.newway.newwayapi.repository.AuthorityRepository;
import com.newway.newwayapi.repository.DeveloperRepository;
import com.newway.newwayapi.security.AuthoritiesConstants;
import com.newway.newwayapi.security.SecurityUtils;
import com.newway.newwayapi.security.jwt.TokenProvider;
import com.newway.newwayapi.service.dto.RegisterDTO;
import com.newway.newwayapi.util.RandomUtil;
import com.newway.newwayapi.web.rest.errors.BadRequest;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DeveloperService {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 34;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    TokenProvider tokenProvider;

    public Developer registerDeveloper(RegisterDTO registerDTO) throws RuntimeException {
        if (!checkPasswordLength(registerDTO.getPassword())) {
            throw new BadRequest("password length not valid.[ 6 <= password <= 34 ]");
        }

        if (developerRepository.findOneByUsername(registerDTO.getUsername()).isPresent()) {
            throw new BadRequest("username already used!");
        }
        if (new EmailValidator().isValid(registerDTO.getEmail(), null)
                && developerRepository.findOneByEmail(registerDTO.getEmail()).isPresent()) {
            throw new BadRequest("email already used!");
        }

        Developer developer = new Developer();
        developer.setUsername(registerDTO.getUsername());
        developer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        developer.setEmail(registerDTO.getEmail().toLowerCase());
        // todo changed after email activation
        developer.setActivated(true);
        developer.setActivationKey(RandomUtil.generateActivationKey());

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        developer.setAuthorities(authorities);
        return developerRepository.save(developer);
    }

    public String login(String login, String password, boolean rememberMe) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(login, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication, rememberMe);
    }


    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= PASSWORD_MIN_LENGTH &&
                password.length() <= PASSWORD_MAX_LENGTH;
    }

    public Developer createDeveloper(Developer developer) {
        developer.setPassword(passwordEncoder.encode(RandomUtil.generatePassword()));
        return developerRepository.save(developer);
    }

    @Transactional(readOnly = true)
    public Optional<Developer> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(developerRepository::findOneWithAuthoritiesByUsername);
    }
}
