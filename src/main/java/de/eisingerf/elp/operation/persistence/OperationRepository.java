package de.eisingerf.elp.operation.persistence;

import de.eisingerf.elp.operation.entity.Operation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {}
