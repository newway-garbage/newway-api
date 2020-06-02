package com.newway.newwayapi.security;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.repository.DeveloperRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DeveloperUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DeveloperUserDetailsService.class);

    private DeveloperRepository developerRepository;

    public DeveloperUserDetailsService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return developerRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                    .map(developer -> createSpringSecurityUser(login, developer))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return developerRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
                .map(developer -> createSpringSecurityUser(lowercaseLogin, developer))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    private User createSpringSecurityUser(String lowercaseLogin, Developer developer) {
        if (!developer.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = developer.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(developer.getUsername(),
                developer.getPassword(),
                grantedAuthorities);
    }
}
