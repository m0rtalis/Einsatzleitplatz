package de.eisingerf.elp.operation.controller.dto;

import de.eisingerf.elp.operation.entity.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "OperationName")
public record OperationNameDto(@NotNull UUID id, @NotBlank String name) {
    public static OperationNameDto from(Operation operation) {
        return new OperationNameDto(operation.getId(), operation.getName());
    }
}
