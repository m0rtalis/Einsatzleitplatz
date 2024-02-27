package de.eisingerf.elp.operation.api;

import de.eisingerf.elp.operation.api.dto.OperationDto;
import de.eisingerf.elp.operation.api.dto.OperationNameDto;
import de.eisingerf.elp.operation.api.dto.input.CreateOperationDto;
import de.eisingerf.elp.operation.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "/operations", produces = "application/json", name = "Operation")
public class OperationController {
    private final OperationService operationService;

    @Autowired
    OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/")
    public List<OperationDto> getOperations() {
        var operations = this.operationService.getOperations(10);
        return operations.stream().map(OperationDto::from).toList();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/names")
    public List<OperationNameDto> getOperationNames() {
        // TODO: Show this as not secured in openapi
        var operations = this.operationService.getOperations(10);
        return operations.stream().map(OperationNameDto::from).toList();
    }

    @GetMapping("/{id}")
    public OperationDto getOperation(@PathVariable("id") Long operationId) {
        var operation = this.operationService.getOperation(operationId);
        // TODO: Handle NoSucheElement as NOT FOUND
        return OperationDto.from(operation);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public OperationDto createOperation(@RequestBody CreateOperationDto createOperationDto) {
        var operation = this.operationService.createOperation(createOperationDto.name(), Optional.empty());
        return OperationDto.from(operation);
    }
}
