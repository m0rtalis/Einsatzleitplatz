package de.eisingerf.elp.journal.persistence;

import de.eisingerf.elp.journal.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {}
