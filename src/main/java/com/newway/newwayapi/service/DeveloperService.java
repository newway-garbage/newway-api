package com.newway.newwayapi.service;

import com.newway.newwayapi.model.Authority;
import com.newway.newwayapi.model.Developer;
import com.newway.newwayapi.repository.AuthorityRepository;
import com.newway.newwayapi.repository.DeveloperRepository;
import com.newway.newwayapi.security.AuthoritiesConstants;
import com.newway.newwayapi.service.dto.AuthenticationDTO;
import com.newway.newwayapi.util.RandomUtil;
import com.newway.newwayapi.web.rest.errors.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
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

    public Developer registerDeveloper(AuthenticationDTO authenticationDTO) {
        if (!checkPasswordLength(authenticationDTO.getPassword())) {
            throw new BadRequest("password length not valid.[ 6 <= password <= 34 ]");
        }

        if (developerRepository.findOneByUsername(authenticationDTO.getLogin()).isPresent()) {
            throw new BadRequest("username already used!");
        }
        if (developerRepository.findOneByEmail(authenticationDTO.getEmail()).isPresent()) {
            throw new BadRequest("email already used!");
        }

        Developer developer = new Developer();
        developer.setUsername(authenticationDTO.getLogin());
        developer.setPassword(passwordEncoder.encode(authenticationDTO.getPassword()));
        developer.setEmail(authenticationDTO.getEmail().toLowerCase());
        // todo changed after email activation
        developer.setActivated(true);
        developer.setActivationKey(RandomUtil.generateActivationKey());

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        developer.setAuthorities(authorities);
        return developerRepository.save(developer);
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
}
