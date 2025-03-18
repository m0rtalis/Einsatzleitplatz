package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Schema(name = "JournalEntry")
public record JournalEntryDto(@NotNull UUID id, @NotNull UUID operationId,
							  @NotNull UUID createdBy,
							  @NotNull Instant createdAt, long journalEntryId,
							  @NotNull String text, boolean isDeleted) {

	public static JournalEntryDto from(JournalEntry entry) {
		return new JournalEntryDto(entry.getId(),
								   entry.getOperationId(),
								   entry.getCreatedBy(),
								   entry.getCreatedAt(),
								   entry.getJournalEntryId(),
								   entry.getText(),
								   entry.isDeleted());
	}
}
