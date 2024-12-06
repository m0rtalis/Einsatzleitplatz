package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.entity.ReferenceType;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.shared.realtime.Event;
import de.eisingerf.elp.shared.realtime.EventName;
import de.eisingerf.elp.shared.realtime.EventStream;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

// TODO: Read up and add @Transactional
@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final GetAuthenticatedUserId getAuthenticatedUserId;
    private final EventStream eventStream;


    @Autowired
    JournalService(
            JournalRepository journalRepository,
            GetAuthenticatedUserId getAuthenticatedUserId,
            EventStream eventStream) {
        this.journalRepository = journalRepository;
        this.getAuthenticatedUserId = getAuthenticatedUserId;
        this.eventStream = eventStream;
    }

    public Page<JournalEntry> getJournal(UUID operationId, Pageable pageable) {
        Page<JournalEntry> journal = journalRepository.findByOperationId(operationId, pageable);
        return journal;
    }

    public JournalEntry create(UUID operationId, Component component, String text) {
        Assert.hasText(text, "Text must not be empty");

        UUID userId = getAuthenticatedUserId.getAuthenticatedUserId();
        long journalEntryId = journalRepository.findNextJournalEntryId(operationId);
        var journalEntry = new JournalEntry(operationId, component, journalEntryId, text, userId);
        var savedEntry = this.journalRepository.save(journalEntry);
        eventStream.send(new Event(EventName.NEW_JOURNAL_ENTRY, savedEntry));
        return savedEntry;
    }

    public void reference(UUID id, UUID referencedEntryId, ReferenceType type) {
        var mainEntry = journalRepository.findById(id).orElseThrow();
        this.create(mainEntry.getOperationId(), Component.JOURNAL, "Reference entry " + referencedEntryId + " as " + type);
        var referencedEntry = journalRepository.findById(referencedEntryId).orElseThrow();
        this.referenceWithoutJournalEntry(mainEntry, referencedEntry, type);
    }

    protected void referenceWithoutJournalEntry(JournalEntry mainEntry, JournalEntry referencedEntry, ReferenceType type) {
        if (!mainEntry.getOperationId().equals(referencedEntry.getOperationId())) {
            // TODO: Rollback
            throw new RuntimeException();
        }
        mainEntry.addReference(referencedEntry, type);
        journalRepository.save(mainEntry);
    }

    public JournalEntry update(UUID id, String event) {
        return null;
    }

    @Transactional
    public void delete(UUID id, @Nullable String text) {
        JournalEntry entry = journalRepository.findById(id).orElseThrow();
        if (entry.isDeleted()) {
            throw new RuntimeException();
        }
        entry.setDeleted(true);
        journalRepository.save(entry);
        // TODO: I18N this and every other hardcoded string
        String deleteDescription = "Delete entry " + entry.getJournalEntryId();
        JournalEntry deleteEntry =
                this.create(entry.getOperationId(), Component.JOURNAL, text != null ? text + "\n" + deleteDescription : deleteDescription);
        this.referenceWithoutJournalEntry(entry, deleteEntry, ReferenceType.DELETE);
    }

    public JournalEntry get(UUID id) {
        return journalRepository.findById(id).orElseThrow();
    }
}
