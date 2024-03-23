package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final GetAuthenticatedUserId getAuthenticatedUserId;

    @Autowired
    JournalService(JournalRepository journalRepository, GetAuthenticatedUserId getAuthenticatedUserId) {
        this.journalRepository = journalRepository;
        this.getAuthenticatedUserId = getAuthenticatedUserId;
    }

    public JournalEntry create(UUID operationId, Component component, String event) {
        Assert.hasText(event, "Event must have text");

        UUID userId = getAuthenticatedUserId.getAuthenticatedUserId();
        long journalEntryId = journalRepository.findNextJournalEntryId(operationId);
        var journalEntry = new JournalEntry(operationId, component, journalEntryId, event, userId, new Date());
        return this.journalRepository.save(journalEntry);
    }

}
