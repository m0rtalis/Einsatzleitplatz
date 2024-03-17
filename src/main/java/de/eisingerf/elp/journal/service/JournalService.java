package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalComponent;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public JournalEntry create(Long operationId, JournalComponent component, String type, String event) {
        Assert.notNull(operationId, "OperationId must be specified");
        Assert.hasText(event, "Event must have text");

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        var journalEntry = new JournalEntry(operationId, component, new JournalType(type), event, UUID.randomUUID(), new Date());
        return this.journalRepository.save(journalEntry);
    }
}
