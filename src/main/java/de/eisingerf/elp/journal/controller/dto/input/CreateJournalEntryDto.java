package de.eisingerf.elp.journal.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "CreateJournalEntry")
public record CreateJournalEntryDto(@NotNull UUID operationId, @NotBlank String text) {
}
