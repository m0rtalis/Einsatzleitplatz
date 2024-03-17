package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JournalEntry")
public record JournalEntryDto(String type, String event) {
    public static JournalEntryDto from(JournalEntry entry) {
        return new JournalEntryDto(entry.getTypeName(), entry.getEvent());
    }
}
