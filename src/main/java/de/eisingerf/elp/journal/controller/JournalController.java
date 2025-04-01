package de.eisingerf.elp.journal.controller;

import de.eisingerf.elp.common.api.rest.list.input.OffsetPaginationParameter;
import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.JournalEntryListDto;
import de.eisingerf.elp.journal.controller.dto.UpdateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.DeleteJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.PatchJournalEntryDto;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
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
		JournalEntry entry = journalService.create(createJournalEntryDto.operationId(),
												   createJournalEntryDto.text());
		return JournalEntryDto.from(entry);
	}

	@GetMapping("")
	public JournalEntryListDto getJournal(@RequestParam UUID operationId, OffsetPaginationParameter pagination) {
		var journal = journalService.getJournal(operationId,
												pagination.toPageable());
		return JournalEntryListDto.from(journal);
	}

	@GetMapping("/{id}")
	public JournalEntryDto getJournalEntry(@PathVariable UUID id) {
		var entry = journalService.get(id);
		return JournalEntryDto.from(entry);
	}

	@PutMapping("/{id}")
	public JournalEntryDto updateJournalEntry(@PathVariable UUID id, @RequestBody UpdateJournalEntryDto updateJournalEntryDto) {
		var entry = journalService.update(id, updateJournalEntryDto.text());
		return JournalEntryDto.from(entry);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEntry(@PathVariable UUID id, @RequestBody(required = false) @Nullable DeleteJournalEntryDto deleteJournalEntryDto) {
		// TODO: Should be idempotent. Either catch exception that's thrown if entry is already deleted or don't throw exception in first place
		journalService.delete(id,
							  deleteJournalEntryDto != null ? deleteJournalEntryDto.reason() : null);
	}

	@PatchMapping("/{id}")
	public JournalEntryDto restoreEntry(@PathVariable UUID id, @RequestBody PatchJournalEntryDto patchJournalEntryDto) {
		Assert.isTrue(patchJournalEntryDto.isDeleted() == null || !patchJournalEntryDto.isDeleted(),
					  "Use DELETE to delete journal entry");
		if (patchJournalEntryDto.isDeleted() != null) {
			// isDeleted MUST be true here
			var entry = journalService.restore(id);
			return JournalEntryDto.from(entry);
		}
		return null;
	}
}
