package de.eisingerf.elp.journal.persistence;

import de.eisingerf.elp.journal.entity.JournalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JournalTypeRepository extends JpaRepository<JournalType, UUID> {
    Optional<JournalType> findByName(String name);
}
