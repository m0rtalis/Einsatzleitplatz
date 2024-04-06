package de.eisingerf.elp.user.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Login")
public record LoginDto(@NotBlank String username, @NotBlank String password) {}
