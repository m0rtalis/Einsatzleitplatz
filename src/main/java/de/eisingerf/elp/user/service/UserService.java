package de.eisingerf.elp.user.service;

import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import de.eisingerf.elp.user.entity.UserDetail;
import de.eisingerf.elp.user.persistence.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements GetAuthenticatedUserId {

    private final UserDetailRepository userDetailRepository;

    @Autowired
    UserService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("No user authenticated");
        }
        String username = authentication.getName();
        UserDetail userDetail = userDetailRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username " + username + " not found"));
        return userDetail.getId();
    }

    public void login(String name) {
        // TODO: Publish login event for diary
    }
}
