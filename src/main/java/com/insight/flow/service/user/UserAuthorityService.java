package com.insight.flow.service.user;

import com.insight.flow.entity.user.User;
import com.insight.flow.entity.user.UserAuthority;
import com.insight.flow.repository.user.UserAuthorityRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthorityService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityService.class);

    public final UserAuthorityRepository userAuthorityRepository;

    public UserAuthorityService(final UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    public void saveAllAndFlush(@NotNull final User user, @NotNull final List<UserAuthority> authorities) {

        logger.info("saveAllAndFlush() -> {}, {}", user, authorities);

        Optional.of(authorities)
                .ifPresent(bdAuthorities -> bdAuthorities.forEach(authority -> {
                    authority.setUser(user);
                    userAuthorityRepository.save(authority);
                }));


    }

}
