package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Entity(name = "journal")
@Table(
        uniqueConstraints =
                @UniqueConstraint(
                        name = "UniqueJournalEntry",
                        columnNames = {"OPERATION_ID", "JOURNAL_ENTRY_ID"}), indexes = @Index(name = "DateIndex", columnList = "CREATED_AT"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class JournalEntry {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id = IdGenerator.generate();

    @Version
    private Integer version;

    @Column(name = "JOURNAL_ENTRY_ID")
    @Positive
    @ToString.Include
    @NotNull
    @Getter
    private long journalEntryId;

    @Column // FIXME: Create foreign key
    @ToString.Include
    @NotNull
    @Getter
    private UUID operationId;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    @Getter
    private Component component;

    @Column(name = "EVENT")
    @Lob
    @NotNull
    @Getter
    private String event;

//    @ManyToOne(targetEntity = UserDetail.class)
//    @JoinColumn(name = "CREATED_BY")
    @Column // FIXME: Create foreign key
    @NotNull
    @Getter
    private UUID createdBy;

    @Column(name = "CREATED_AT")
    @NotNull
    @Getter
    private Date createdAt;

    protected JournalEntry() {}

    public JournalEntry(
            UUID operationId,
            Component component,
            long journalEntryId,
            String event,
            UUID createdBy,
            Date createdAt) {
        this.operationId = operationId;
        this.component = component;
        this.journalEntryId = journalEntryId;
        this.event = event;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

}
