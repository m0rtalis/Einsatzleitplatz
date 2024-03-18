package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalComponent;
import de.eisingerf.elp.journal.persistence.JournalComponentRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalComponentService {
    private final JournalComponentRepository journalComponentRepository;

    public static JournalComponent JOURNAL;
    public static JournalComponent OPERATION;

    @Autowired
    JournalComponentService(JournalComponentRepository journalComponentRepository) {
        this.journalComponentRepository = journalComponentRepository;
    }

    @PostConstruct
    private void initialize() {
        JournalComponentService.JOURNAL = findOrCreate("Journal");
        JournalComponentService.OPERATION = findOrCreate("Operation");
    }

    JournalComponent findOrCreate(String name) {
        return journalComponentRepository.findByComponent(name).orElse(journalComponentRepository.save(new JournalComponent(name)));
    }
}
