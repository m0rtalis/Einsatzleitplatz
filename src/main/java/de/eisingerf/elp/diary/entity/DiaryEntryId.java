package de.eisingerf.elp.diary.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record DiaryEntryId(long operationId, long entryId) implements Serializable {
}
