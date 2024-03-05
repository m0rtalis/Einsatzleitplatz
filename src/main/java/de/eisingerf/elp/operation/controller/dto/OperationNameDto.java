package de.eisingerf.elp.operation.controller.dto;

import de.eisingerf.elp.operation.entity.Operation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "OperationName")
public record OperationNameDto(Long id, String name) {
    public static OperationNameDto from(Operation operation) {
        return new OperationNameDto(operation.getId(), operation.getName());
    }
}
