package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

@Schema(name = "JournalEntry")
public record JournalEntryDto(UUID operationId, UUID createdBy, Component component, Date createdAt,
							  long journalEntryId, String event) {
	public static JournalEntryDto from(JournalEntry entry) {
		return new JournalEntryDto(entry.getOperationId(), entry.getCreatedBy(), entry.getComponent(), entry.getCreatedAt(), entry.getJournalEntryId(), entry.getEvent());
	}
}
