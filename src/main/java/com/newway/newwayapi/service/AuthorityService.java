package com.newway.newwayapi.service;

import com.newway.newwayapi.NewwayApiApplication;
import com.newway.newwayapi.model.Authority;
import com.newway.newwayapi.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.newway.newwayapi.security.AuthoritiesConstants.ADMIN;
import static com.newway.newwayapi.security.AuthoritiesConstants.USER;

@Service
public class AuthorityService {

    private static final Logger log = LoggerFactory.getLogger(NewwayApiApplication.class);

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public void saveRoles() {
        try {
            List<Authority> authorities = new ArrayList<>();
            Authority a = new Authority();
            a.setName(ADMIN);
            authorities.add(a);
            a = new Authority();
            a.setName(USER);
            authorities.add(a);
            authorityRepository.saveAll(authorities);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
