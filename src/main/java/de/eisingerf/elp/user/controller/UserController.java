package de.eisingerf.elp.user.controller;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import de.eisingerf.elp.common.api.rest.list.input.OffsetPaginationParameter;
import de.eisingerf.elp.user.controller.dto.UserDto;
import de.eisingerf.elp.user.controller.dto.UserListDto;
import de.eisingerf.elp.user.controller.dto.input.LoginDto;
import de.eisingerf.elp.user.entity.UserAuthentication;
import de.eisingerf.elp.user.persistence.UserRepository;
import de.eisingerf.elp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isFullyAuthenticated()")
@RequestMapping(path = "/users", name = "User")
public class UserController {

    private final UserService userService;
    // TODO: Eventually create UserDetail with (Profile, Full name etc
    //  which gets returned instead of accessing repository with security infos here
    private final UserRepository userRepository;

    UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public UserListDto getUsers(OffsetPaginationParameter pagination) {
        return null;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id) {
        UserAuthentication userAuthentication = userRepository.findById(id).orElseThrow();
        return UserDto.from(userAuthentication);
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto login(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        // This might circumvent Spring Security Defaults like session rewriting
        // https://stackoverflow.com/questions/4664893/how-to-manually-set-an-authenticated-user-in-spring-security-springmvc#comment81416271_8336233
        var loginRes = userService.login(loginDto.username(), loginDto.password());
        Authentication authentication = loginRes.second();
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return new UserDto(loginRes.first().getId(), authentication.getName());
    }
}
