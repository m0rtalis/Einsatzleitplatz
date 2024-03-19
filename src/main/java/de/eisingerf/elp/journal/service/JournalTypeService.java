package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.journal.persistence.JournalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalTypeService {

    private final JournalTypeRepository journalTypeRepository;

    @Autowired
    JournalTypeService(JournalTypeRepository journalTypeRepository){
        this.journalTypeRepository = journalTypeRepository;
    }

    public JournalType create(String name) {
        JournalType journalType = new JournalType(name);
        return journalTypeRepository.save(journalType);
    }

    public JournalType findOrCreate(String name) {
		return journalTypeRepository.findByName(name).orElseGet(() -> create(name));
    }

    public List<JournalType> findAll() {
        return journalTypeRepository.findAll();
    }
}
