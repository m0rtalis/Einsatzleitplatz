package de.eisingerf.elp.user.controller.dto;

import de.eisingerf.elp.user.entity.UserAuthentication;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "User")
public record UserDto(@NotNull UUID id, @NotBlank String username) {
    public static UserDto from(UserAuthentication userAuthentication) {
        return new UserDto(userAuthentication.getId(), userAuthentication.getUsername());
    }
}
