package com.newway.newwayapi.service;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.repository.AuthorityRepository;
import com.newway.newwayapi.repository.DeveloperRepository;
import com.newway.newwayapi.security.SecurityUtils;
import com.newway.newwayapi.security.jwt.TokenProvider;
import com.newway.newwayapi.service.dto.RegisterDTO;
import com.newway.newwayapi.util.RandomUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class DeveloperService {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 34;

    private PasswordEncoder passwordEncoder;

    private DeveloperRepository developerRepository;

    private AuthorityRepository authorityRepository;

    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private TokenProvider tokenProvider;

    public DeveloperService(PasswordEncoder passwordEncoder, DeveloperRepository developerRepository,
                            AuthorityRepository authorityRepository, AuthenticationManagerBuilder authenticationManagerBuilder,
                            TokenProvider tokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.developerRepository = developerRepository;
        this.authorityRepository = authorityRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    public Developer registerDeveloper(RegisterDTO registerDTO) throws RuntimeException {
        String encode = passwordEncoder.encode(registerDTO.getPassword());

        System.out.println(encode);

        boolean admin = passwordEncoder.matches("admin", encode);
        System.out.println(admin);
//        if (!checkPasswordLength(registerDTO.getPassword())) {
//            throw new BadRequest("password length not valid.[ 6 <= password <= 34 ]");
//        }
//
//        if (developerRepository.findOneByUsername(registerDTO.getUsername()).isPresent()) {
//            throw new BadRequest("username already used!");
//        }
//        if (new EmailValidator().isValid(registerDTO.getEmail(), null)
//                && developerRepository.findOneByEmail(registerDTO.getEmail()).isPresent()) {
//            throw new BadRequest("email already used!");
//        }
//
//        Developer developer = new Developer();
//        developer.setUsername(registerDTO.getUsername());
//        String encode = passwordEncoder.encode(registerDTO.getPassword());
//        System.out.println(encode);
//        developer.setPassword(encode);
//        developer.setEmail(registerDTO.getEmail().toLowerCase());
//        // todo changed after email activation
//        developer.setActivated(true);
//        developer.setActivationKey(RandomUtil.generateActivationKey());
//
//        Set<Authority> authorities = new HashSet<>();
//        authorityRepository.findById(AuthoritiesConstants.DEVELOPER).ifPresent(authorities::add);
//        developer.setAuthorities(authorities);
//        return developerRepository.save(developer);
        return null;
    }

    public String login(String login, String password, boolean rememberMe) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(login, password);
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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
