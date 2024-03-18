package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import de.eisingerf.elp.operation.entity.Operation;
import de.eisingerf.elp.user.entity.UserDetail;
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
                        columnNames = {"JOURNAL_ENTRY_ID", "OPERATION_ID"}))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class JournalEntry {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id = IdGenerator.generate();

    @Column(name = "JOURNAL_ENTRY_ID")
    @Positive
    @ToString.Include
    @NotNull
    private Long journalEntryId = 1L;  // TODO: Calculate or use custom generator?

    @ManyToOne(targetEntity = Operation.class, optional = false)
    @JoinColumn(name = "OPERATION_ID")
    @ToString.Include
    @NotNull
    private UUID operationId;

    @ManyToOne
    @NotNull
    private JournalComponent component;

    @ManyToOne
    @NotNull
    @ToString.Include
    private JournalType type;

    @Column(name = "EVENT")
    @NotNull
    @Getter
    private String event;

    @ManyToOne(targetEntity = UserDetail.class)
    @JoinColumn(name = "CREATED_BY")
    @NotNull
    private UUID createdBy;

    @Column(name = "CREATED_AT")
    @NotNull
    private Date createdAt;

    protected JournalEntry() {}

    public JournalEntry(
            UUID operationId,
            JournalComponent component,
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
