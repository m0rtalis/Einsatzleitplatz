package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

@Schema(name = "JournalEntry")
public record JournalEntryDto(@NotNull UUID id, @NotNull UUID operationId, @NotNull UUID createdBy, @NotNull ComponentDto component, @NotNull Date createdAt,
							  @NotNull long journalEntryId, @NotNull String text) {
	public static JournalEntryDto from(JournalEntry entry) {
		return new JournalEntryDto(entry.getId(), entry.getOperationId(), entry.getCreatedBy(), ComponentDto.from(entry.getComponent()), entry.getCreatedAt(), entry.getJournalEntryId(), entry.getText());
	}
}
