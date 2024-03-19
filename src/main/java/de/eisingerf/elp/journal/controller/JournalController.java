package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.journal.service.JournalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "/journal", produces = "application/json", name = "Operation")
public class JournalController {

	private final JournalService journalService;
	private final JournalTypeService journalTypeService;

	@Autowired
	JournalController(
			JournalService journalService,
			JournalTypeService journalTypeService) {
		this.journalService = journalService;
		this.journalTypeService = journalTypeService;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public JournalEntryDto createJournalEntry(@RequestBody CreateJournalEntryDto createJournalEntryDto) {
		JournalType journalType = journalTypeService.findOrCreate(createJournalEntryDto.type());
		JournalEntry entry = journalService.create(
				createJournalEntryDto.operationId(),
				Component.JOURNAL,
				journalType,
				createJournalEntryDto.event()
		);
		return JournalEntryDto.from(entry);
	}

	@GetMapping("/types")
	public Collection<String> getJournalTypes() {
		return this.journalTypeService.findAll().stream().map(JournalType::getName).toList();
	}
}
