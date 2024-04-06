package de.eisingerf.elp.journal.persistence;

import de.eisingerf.elp.journal.entity.JournalEntry;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

    @Query("select COUNT(*) + 1 from journal where operationId = ?1")
    long findNextJournalEntryId(UUID operationId);

    Page<JournalEntry> findByOperationId(UUID operationId, Pageable pageable);
}
