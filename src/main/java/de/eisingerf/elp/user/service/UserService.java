package de.eisingerf.elp.user.service;

import de.eisingerf.elp.common.type.Tuple;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import de.eisingerf.elp.user.entity.UserAuthentication;
import de.eisingerf.elp.user.event.UserEventPublisher;
import de.eisingerf.elp.user.persistence.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements GetAuthenticatedUserId {

    private final UserRepository userAuthenticationRepository;
    private final AuthenticationManager authenticationManager;
    private final UserEventPublisher userEventPublisher;

    @Autowired
    UserService(
            UserRepository userAuthenticationRepository,
            AuthenticationManager authenticationManager,
            UserEventPublisher userEventPublisher) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.authenticationManager = authenticationManager;
        this.userEventPublisher = userEventPublisher;
    }

    public Tuple<UserAuthentication, Authentication> login(String username, String password) {
        // TODO: Journal entry for login and logout
        Authentication authentication =
                this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        var user = userAuthenticationRepository.findByUsername(username).orElseThrow();
        userEventPublisher.publishUserSignedIn(user.getId(), username);
        return new Tuple<>(user, authentication);
    }

    @Override
    public UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No user authenticated");
        }
        String username = authentication.getName();
        UserAuthentication userAuthentication = userAuthenticationRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username " + username + " not found"));
        return userAuthentication.getId();
    }
}
