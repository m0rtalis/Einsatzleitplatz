package de.eisingerf.elp.operation.event;

import de.eisingerf.elp.operation.entity.Operation;
import de.eisingerf.elp.shared.operation.event.OperationCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OperationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    OperationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishOperationCreated(Operation operation) {
        this.applicationEventPublisher.publishEvent(new OperationCreatedEvent(operation.getId(), operation.getName()));
    }
}
