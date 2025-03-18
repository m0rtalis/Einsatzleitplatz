package de.eisingerf.elp.user.service;

import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import de.eisingerf.elp.user.entity.UserAuthentication;
import de.eisingerf.elp.user.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements GetAuthenticatedUserId {

	private final UserRepository userAuthenticationRepository;

	@Autowired
	UserService(UserRepository userAuthenticationRepository) {
		this.userAuthenticationRepository = userAuthenticationRepository;
	}

	@Override
	public UUID getAuthenticatedUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("No user authenticated");
		}
		String username = authentication.getName();
		UserAuthentication userAuthentication = userAuthenticationRepository.findByUsername(
				username).orElseThrow(() -> new RuntimeException("Username " + username + " not found"));
		return userAuthentication.getId();
	}
}
