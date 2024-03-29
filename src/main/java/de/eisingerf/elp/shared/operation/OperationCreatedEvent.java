package de.eisingerf.elp.shared.operation;

import java.util.UUID;

public record OperationCreatedEvent(UUID operationId, String name) { }
