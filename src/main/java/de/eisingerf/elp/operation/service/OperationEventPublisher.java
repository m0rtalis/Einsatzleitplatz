package de.eisingerf.elp.operation.service;

import de.eisingerf.elp.operation.entity.Operation;
import de.eisingerf.elp.shared.operation.event.OperationCreatedEvent;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OperationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GetAuthenticatedUserId getAuthenticatedUserId;

    @Autowired
    OperationEventPublisher(ApplicationEventPublisher applicationEventPublisher, GetAuthenticatedUserId getAuthenticatedUserId) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.getAuthenticatedUserId = getAuthenticatedUserId;
    }

    public void publishOperationCreated(Operation operation) {
        this.applicationEventPublisher.publishEvent(new OperationCreatedEvent(operation.getId(), operation.getName(), getAuthenticatedUserId.getAuthenticatedUserId()));
    }
}
