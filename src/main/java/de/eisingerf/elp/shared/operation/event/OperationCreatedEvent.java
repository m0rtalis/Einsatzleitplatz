package de.eisingerf.elp.shared.operation.event;

import de.eisingerf.elp.common.event.ApplicationEvent;

import java.util.UUID;

public record OperationCreatedEvent(UUID operationId, String name, UUID createdBy) implements ApplicationEvent { }
