package de.eisingerf.elp.user.controller;

import de.eisingerf.elp.user.controller.dto.UserDto;
import de.eisingerf.elp.user.controller.dto.input.LoginDto;
import de.eisingerf.elp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@PreAuthorize("isFullyAuthenticated()")
@RequestMapping(path = "/users", name = "User")
public class UserController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
	}

    // This may seem like logic in the controller, but we do not want the services to know about rest, sessions or similar
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public UserDto login(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        // This might circumvent Spring Security Defaults like session rewriting
        // https://stackoverflow.com/questions/4664893/how-to-manually-set-an-authenticated-user-in-spring-security-springmvc#comment81416271_8336233
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
        userService.login(authentication.getName());
        return new UserDto(authentication.getName());
    }

    @GetMapping("/me")
    public UserDto authenticatedUser(Principal principal) {
        return new UserDto(principal.getName());
    }
}
