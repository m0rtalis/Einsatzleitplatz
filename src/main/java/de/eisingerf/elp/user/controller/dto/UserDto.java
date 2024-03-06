package de.eisingerf.elp.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "User")
public record UserDto(@NotBlank String username) {
}
