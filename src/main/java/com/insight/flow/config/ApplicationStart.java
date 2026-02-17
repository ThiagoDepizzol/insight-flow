package com.insight.flow.config;

import com.insight.flow.service.user.UserApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ApplicationStart implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(ApplicationStart.class);

    private final UserApplicationService userApplicationService;

    public ApplicationStart(final UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Override
    public void run(String... args) {
        try {

            log.info("Starting configuration");

            userApplicationService.newSystemAdminConfiguration();

        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error starting application");
        }
    }

}
