package de.eisingerf.elp.journal.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "CreateJournalEntry")
public record CreateJournalEntryDto(UUID operationId, String type, String event) {
}
