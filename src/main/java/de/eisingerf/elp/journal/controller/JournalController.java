package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.common.api.rest.list.input.OffsetPaginationParameter;
import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.JournalEntryListDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.DeleteJournalEntryDto;
import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.journal.service.JournalService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "/journal", produces = "application/json", name = "Operation")
public class JournalController {

    private final JournalService journalService;
    private final JournalRepository journalRepository;

    @Autowired
    JournalController(JournalService journalService, JournalRepository journalRepository) {
        this.journalService = journalService;
        this.journalRepository = journalRepository;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public JournalEntryDto createJournalEntry(@RequestBody CreateJournalEntryDto createJournalEntryDto) {
        JournalEntry entry = journalService.create(
                createJournalEntryDto.operationId(), Component.JOURNAL, createJournalEntryDto.text());
        return JournalEntryDto.from(entry);
    }

    @GetMapping("")
    public JournalEntryListDto getJournal(@RequestParam UUID operationId, OffsetPaginationParameter pagination) {
        Page<JournalEntry> journal = journalRepository.findByOperationId(operationId, pagination.toPageable());
        return JournalEntryListDto.from(journal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(@PathVariable UUID id, @RequestBody DeleteJournalEntryDto deleteJournalEntryDto) {
        journalService.delete(id, deleteJournalEntryDto.text());
    }
}
