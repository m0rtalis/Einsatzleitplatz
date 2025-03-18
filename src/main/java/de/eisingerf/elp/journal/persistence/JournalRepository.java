package de.eisingerf.elp.journal.persistence;

import de.eisingerf.elp.journal.entity.JournalEntry;
import java.util.UUID;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

    // SUGG: Optimization is to have the journal entry id separate
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COUNT(*) + 1 FROM journal WHERE operationId = ?1")
    long findNextJournalEntryId(UUID operationId);

    Page<JournalEntry> findByOperationId(UUID operationId, Pageable pageable);
}
