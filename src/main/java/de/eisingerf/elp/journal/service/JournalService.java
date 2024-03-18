package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalComponent;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

@Service
public class JournalService {

    private final JournalRepository journalRepository;

    @Autowired
    JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public JournalEntry create(UUID operationId, JournalComponent component, JournalType type, String event, UUID userId) {
        Assert.hasText(event, "Event must have text");

        var journalEntry = new JournalEntry(operationId, component, type, event, userId, new Date());
        return this.journalRepository.save(journalEntry);
    }

}
