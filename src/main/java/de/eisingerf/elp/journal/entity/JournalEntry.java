package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.util.Date;
import java.util.UUID;

@Entity(name = "journal")
@Table(indexes = @Index(name = "DateIndex", columnList = "CREATED_AT"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
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
    @NaturalId
    private long journalEntryId;

    @Column // FIXME: Create foreign key
    @ToString.Include
    @NotNull
    @NaturalId
    private UUID operationId;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Component component;

    @Column(name = "TEXT")
    @Lob
    @NotNull
    private String text;

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
            long journalEntryId,
            String text,
            UUID createdBy,
            Date createdAt) {
        this.operationId = operationId;
        this.component = component;
        this.journalEntryId = journalEntryId;
        this.text = text;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

}
