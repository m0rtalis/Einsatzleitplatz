package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
                        columnNames = {"JOURNAL_ENTRY_ID", "OPERATION_ID"}))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class JournalEntry {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id = IdGenerator.generate();

/*
    @Column(name = "JOURNAL_ENTRY_ID")
    @Positive
    @ToString.Include
    @NotNull
    private Long journalEntryId = IdGenerator.generate().getLeastSignificantBits();  // TODO: Calculate or use custom generator
*/

    @Column // FIXME: Create foreign key
    @ToString.Include
    @NotNull
    private UUID operationId;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Component component;

    @ManyToOne
    @NotNull
    private JournalType type;

    @Column(name = "EVENT")
    @NotNull
    @Getter
    private String event;

//    @ManyToOne(targetEntity = UserDetail.class)
//    @JoinColumn(name = "CREATED_BY")
    @Column // FIXME: Create foreign key
    @NotNull
    private UUID createdBy;

    @Column(name = "CREATED_AT")
    @NotNull
    private Date createdAt;

    protected JournalEntry() {}

    public JournalEntry(
            UUID operationId,
            Component component,
            JournalType type,
            String event,
            UUID createdBy,
            Date createdAt) {
        this.operationId = operationId;
        this.component = component;
        this.type = type;
        this.event = event;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public String getTypeName() {
        return this.type.getName();
    }

}
