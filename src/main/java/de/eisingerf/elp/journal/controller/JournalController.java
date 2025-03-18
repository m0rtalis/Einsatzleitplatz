package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.common.api.rest.list.input.OffsetPaginationParameter;
import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.JournalEntryListDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.DeleteJournalEntryDto;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "/journal", produces = "application/json", name = "Operation")
public class JournalController {

    private final JournalService journalService;

    @Autowired
    JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public JournalEntryDto createJournalEntry(@RequestBody CreateJournalEntryDto createJournalEntryDto) {
        JournalEntry entry = journalService.create(
                createJournalEntryDto.operationId(), createJournalEntryDto.text());
        return JournalEntryDto.from(entry);
    }

    @GetMapping("")
    public JournalEntryListDto getJournal(@RequestParam UUID operationId, OffsetPaginationParameter pagination) {
        var journal = journalService.getJournal(operationId, pagination.toPageable());
        return JournalEntryListDto.from(journal);
    }

    @GetMapping("/{id}")
    public JournalEntryDto getJournalEntry(@PathVariable UUID id) {
        var entry = journalService.get(id);
        return JournalEntryDto.from(entry);
    }

    @PutMapping("/{id}")
    public JournalEntryDto updateJournalEntry(@PathVariable UUID id, @RequestBody String text) {
        var entry = journalService.update(id, text);
        return JournalEntryDto.from(entry);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(@PathVariable UUID id, @RequestBody DeleteJournalEntryDto deleteJournalEntryDto) {
        journalService.delete(id, deleteJournalEntryDto.text());
    }
}
