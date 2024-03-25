package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;

import java.util.Date;
import java.util.UUID;

@Schema(name = "JournalEntry")
public record JournalEntryDto(@Nonnull UUID operationId, @Nonnull UUID createdBy, @Nonnull Component component, @Nonnull Date createdAt,
							  long journalEntryId, @Nonnull String text) {
	public static JournalEntryDto from(JournalEntry entry) {
		return new JournalEntryDto(entry.getOperationId(), entry.getCreatedBy(), entry.getComponent(), entry.getCreatedAt(), entry.getJournalEntryId(), entry.getText());
	}
}
