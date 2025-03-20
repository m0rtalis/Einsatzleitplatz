package de.eisingerf.elp.operation.service;

import de.eisingerf.elp.common.location.entity.Location;
import de.eisingerf.elp.operation.entity.Operation;
import de.eisingerf.elp.operation.event.OperationEventPublisher;
import de.eisingerf.elp.operation.persistence.OperationRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final OperationEventPublisher operationEventPublisher;

    @Autowired
    OperationService(OperationRepository operationRepository, OperationEventPublisher operationEventPublisher) {
        this.operationRepository = operationRepository;
        this.operationEventPublisher = operationEventPublisher;
	}

    public List<Operation> getOperations(int size) {
        return operationRepository.findAll(Pageable.ofSize(size)).getContent();
    }

    public Operation getOperation(UUID operationId) {
        return operationRepository.findById(operationId).orElseThrow();
    }

    public Operation createOperation(String name, @Nullable Location location) {
        Assert.hasText(name, "Name must have text");

        var operation = new Operation(name);
        operation.setLocation(location);

        Operation savedOperation = this.operationRepository.save(operation);

        this.operationEventPublisher.publishOperationCreated(savedOperation);

        return savedOperation;
    }
}
