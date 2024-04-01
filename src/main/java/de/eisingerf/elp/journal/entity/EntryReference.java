package de.eisingerf.elp.journal.entity;

import java.util.Date;
import java.util.UUID;

public record EntryReference(UUID referencedEntryId, UUID userId, Date createdAt) {
}
