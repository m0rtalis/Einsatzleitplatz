package de.eisingerf.elp.journal.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateJournalEntry")
public record CreateJournalEntryDto(Long operationId, String type, String event) {
}
