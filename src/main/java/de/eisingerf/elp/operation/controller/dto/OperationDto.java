package de.eisingerf.elp.operation.controller.dto;

import de.eisingerf.elp.operation.entity.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Operation")
public record OperationDto(@NotNull Long id, @NotBlank String name) {
    public static OperationDto from(Operation operation) {
        return new OperationDto(operation.getId(), operation.getName());
    }
}
