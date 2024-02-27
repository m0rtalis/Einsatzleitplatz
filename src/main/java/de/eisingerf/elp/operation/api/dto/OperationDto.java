package de.eisingerf.elp.operation.api.dto;

import de.eisingerf.elp.operation.entity.Operation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Operation")
public record OperationDto(Long id, String name) {
    public static OperationDto from(Operation operation) {
        return new OperationDto(operation.getId(), operation.getName());
    }
}
