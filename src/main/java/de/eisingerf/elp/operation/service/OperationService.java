package de.eisingerf.elp.operation.service;

import de.eisingerf.elp.common.location.entity.Location;
import de.eisingerf.elp.operation.entity.Operation;
import de.eisingerf.elp.operation.persistence.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {
    private final OperationRepository operationRepository;

    @Autowired
    OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public List<Operation> getOperations(int size) {
        return operationRepository.findAll(Pageable.ofSize(size)).getContent();
    }

    public Operation getOperation(Long operationId) {
        return operationRepository.findById(operationId).orElseThrow();
    }

    public Operation createOperation(String name, Optional<Location> location) {
        Assert.hasText(name, "Name must have text");

        var operation = new Operation(name);
        operation.setLocation(location.orElse(null));

        return this.operationRepository.save(operation);
    }
}
