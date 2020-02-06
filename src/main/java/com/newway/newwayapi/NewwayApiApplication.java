package com.newway.newwayapi;

import com.newway.newwayapi.service.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewwayApiApplication implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(NewwayApiApplication.class);


    private final AuthorityService authorityService;

    public NewwayApiApplication(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public static void main(String[] args) {
        SpringApplication.run(NewwayApiApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            authorityService.saveRoles();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}

