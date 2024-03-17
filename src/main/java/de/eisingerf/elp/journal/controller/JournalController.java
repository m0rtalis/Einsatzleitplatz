package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.entity.JournalComponent;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "/journal", produces = "application/json", name = "Operation")
public class JournalController {

    private final JournalService journalService;

    @Autowired
    JournalController(JournalService journalService){
        this.journalService = journalService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public JournalEntryDto createJournalEntry(@RequestBody CreateJournalEntryDto createJournalEntryDto) {
        JournalEntry entry = journalService.create(
                createJournalEntryDto.operationId(),
                JournalComponent.JOURNAL,
                createJournalEntryDto.type(),
                createJournalEntryDto.event());
        return JournalEntryDto.from(entry);
    }
}
