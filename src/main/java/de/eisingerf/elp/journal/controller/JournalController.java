package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.entity.JournalComponent;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.journal.service.JournalComponentService;
import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.journal.service.JournalTypeService;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
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
    private final GetAuthenticatedUserId getAuthenticatedUserId;
    private final JournalTypeService journalTypeService;

    @Autowired
    JournalController(
            JournalService journalService,
            GetAuthenticatedUserId getAuthenticatedUserId,
            JournalTypeService journalTypeService) {
        this.journalService = journalService;
        this.getAuthenticatedUserId = getAuthenticatedUserId;
        this.journalTypeService = journalTypeService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public JournalEntryDto createJournalEntry(@RequestBody CreateJournalEntryDto createJournalEntryDto) {
        UUID userId = getAuthenticatedUserId.getAuthenticatedUserId();
        JournalType journalType = journalTypeService.findOrCreate(createJournalEntryDto.type());
        JournalEntry entry = journalService.create(
                createJournalEntryDto.operationId(),
                JournalComponentService.JOURNAL,
                journalType,
                createJournalEntryDto.event(),
                userId);
        return JournalEntryDto.from(entry);
    }
}
