package de.eisingerf.elp.journal.persistence;

import de.eisingerf.elp.journal.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

	@Query("select COUNT(*) + 1 from journal where operationId = ?1")
	long findNextJournalEntryId(UUID operationId);
}
