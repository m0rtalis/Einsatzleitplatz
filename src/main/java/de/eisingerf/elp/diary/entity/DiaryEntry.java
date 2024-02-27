package de.eisingerf.elp.diary.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "diary")
public class DiaryEntry {

    @EmbeddedId
    private DiaryEntryId id;

    @Column(name = "TEXT", nullable = false)
    private String text;

    @Column(name = "COMPONENT", nullable = false)
    private String component;

    @Column(name = "CREATED_BY", nullable = false)
    private long createdBy;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    public long getOperationId() {
        return id.operationId();
    }

    public long getEntryId() {
        return id.entryId();
    }
}
