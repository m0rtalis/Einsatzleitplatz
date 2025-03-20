package de.eisingerf.elp.user.service;

import de.eisingerf.elp.common.type.Tuple;
import de.eisingerf.elp.user.entity.UserAuthentication;
import de.eisingerf.elp.user.event.UserEventPublisher;
import de.eisingerf.elp.user.persistence.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserActionService {

	private final UserRepository userAuthenticationRepository;
	private final AuthenticationManager authenticationManager;
	private final UserEventPublisher userEventPublisher;

	public UserActionService(UserRepository userAuthenticationRepository, AuthenticationManager authenticationManager, UserEventPublisher userEventPublisher) {
		this.userAuthenticationRepository = userAuthenticationRepository;
		this.authenticationManager = authenticationManager;
		this.userEventPublisher = userEventPublisher;
	}

	public Tuple<UserAuthentication, Authentication> login(String username, String password) {
		Authentication authentication =
				this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		var user = userAuthenticationRepository.findByUsername(username).orElseThrow();

		userEventPublisher.publishUserSignedIn(user.getId(), username);
		return new Tuple<>(user, authentication);
	}
}
