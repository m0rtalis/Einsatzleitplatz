package de.eisingerf.elp.user.controller;

import de.eisingerf.elp.user.controller.dto.input.LoginDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.session.WebSessionManager;

@RestController("/user")
public class UserController {


    private final AuthenticationManager authenticationManager;

    UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public void login(@RequestBody LoginDto loginDto) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
    }
}
