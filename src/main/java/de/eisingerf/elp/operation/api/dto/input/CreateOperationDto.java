package de.eisingerf.elp.operation.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CreateBasket")
public record CreateOperationDto(@NotBlank String name) {
}
