package de.eisingerf.elp.operation.api;

import de.eisingerf.elp.operation.api.dto.OperationNameDto;
import de.eisingerf.elp.operation.api.dto.input.CreateOperationDto;
import de.eisingerf.elp.operation.api.dto.OperationDto;
import de.eisingerf.elp.operation.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/operations", produces = "application/json")
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

    @GetMapping("/names")
    public List<OperationNameDto> getOperationNames() {
        var operations = this.operationService.getOperations(10);
        return operations.stream().map(OperationNameDto::from).toList();
    }

    @GetMapping("/{id}")
    public OperationDto getOperation(@PathVariable("id") Long operationId) {
        var operation = this.operationService.getOperation(operationId);
        return OperationDto.from(operation);
    }

    @PostMapping("/")
    public OperationDto createOperation(@RequestBody CreateOperationDto createOperationDto) {
        var operation = this.operationService.createOperation(createOperationDto.name(), Optional.empty());
        return OperationDto.from(operation);
    }
}
