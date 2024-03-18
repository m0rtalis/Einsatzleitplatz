package de.eisingerf.elp.journal.persistence;

import de.eisingerf.elp.journal.entity.JournalComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JournalComponentRepository extends JpaRepository<JournalComponent, UUID> {
    Optional<JournalComponent> findByComponent(String component);
}
