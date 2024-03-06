package de.eisingerf.elp.operation.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CreateOperation")
public record CreateOperationDto(@NotBlank String name) {
}
