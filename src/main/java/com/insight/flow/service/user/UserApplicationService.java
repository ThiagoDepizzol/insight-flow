package com.insight.flow.service.user;

import com.insight.flow.entity.user.User;
import com.insight.flow.entity.user.UserAuthority;
import com.insight.flow.repository.user.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;

@Service
public class UserApplicationService {

    public final PasswordEncoder encoder;

    public final UserService userService;

    public final UserRepository userRepository;


    @Lazy
    public UserApplicationService(final UserService userService, final UserRepository userRepository,
                                  final PasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User loadAuthorities(String email) {
        return userRepository.findByEmailWithAuthorities(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public void newSystemAdminConfiguration() {

        final String email = "admin@fiap.com.br";

        if (userRepository.findByEmailAndActiveTrue(email).isEmpty()) {

            final User admin = new User();

            admin.setActive(true);
            admin.setActivated(true);
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail(email);
            admin.setPassword("admin");
            admin.setLastModifiedDate(Instant.now());

            admin.setRoles(new HashSet<>());

            final UserAuthority newAuthority = new UserAuthority();

            newAuthority.setActive(true);
            newAuthority.setAuthority("SYSTEM_ADMIN");

            admin.getRoles().add(newAuthority);

            userService.created(admin);
        }
    }

}
