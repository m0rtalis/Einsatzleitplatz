package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.common.api.rest.ListDto;
import de.eisingerf.elp.common.api.rest.input.PaginationParameter;
import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.JournalSortField;
import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.journal.service.JournalService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController()
@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "/journal", produces = "application/json", name = "Operation")
public class JournalController {

	private final JournalService journalService;
	private final JournalRepository journalRepository;

	@Autowired
	JournalController(
			JournalService journalService,
			JournalRepository journalRepository
	) {
		this.journalService = journalService;
		this.journalRepository = journalRepository;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public JournalEntryDto createJournalEntry(@RequestBody CreateJournalEntryDto createJournalEntryDto) {
		JournalEntry entry = journalService.create(
				createJournalEntryDto.operationId(),
				Component.JOURNAL,
				createJournalEntryDto.event()
		);
		return JournalEntryDto.from(entry);
	}

	@GetMapping("")
	public ListDto<JournalEntryDto> getJournal(@RequestParam Optional<UUID> operationId, @ParameterObject PaginationParameter<JournalSortField> paginationParameter) {
		journalRepository.findAll();
		return ListDto.fromSlice(new PageImpl<>(new ArrayList<>()));
	}


	@GetMapping("/othter")
	public ListDto<String> others() {
		return ListDto.fromSlice(new PageImpl<>(new ArrayList<>()));
	}
}
