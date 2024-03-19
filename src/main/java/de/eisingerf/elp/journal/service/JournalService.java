package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.entity.JournalType;
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

    public JournalEntry create(UUID operationId, Component component, JournalType type, String event) {
        Assert.hasText(event, "Event must have text");

        UUID userId = getAuthenticatedUserId.getAuthenticatedUserId();
        var journalEntry = new JournalEntry(operationId, component, type, event, userId, new Date());
        return this.journalRepository.save(journalEntry);
    }

}
