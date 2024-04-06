package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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

    @Column(name = "JOURNAL_ENTRY_ID", updatable = false)
    @Positive @ToString.Include
    @NotNull @NaturalId
    private long journalEntryId;

    @Column(updatable = false)
    @ToString.Include
    @NotNull @NaturalId
    private UUID operationId;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull private Component component;

    @Column(name = "TEXT", updatable = false)
    @Lob
    @NotNull private String text;

    //    @ManyToOne(targetEntity = UserDetail.class)
    //    @JoinColumn(name = "CREATED_BY")
    @Column(updatable = false) // TODO: Create foreign key, for others as well
    @NotNull @CreatedBy
    private UUID createdBy;

    @Column(name = "CREATED_AT", updatable = false)
    @NotNull @CreatedDate
    private Instant createdAt;

    @Column(name = "IS_DELETED")
    @Setter
    @NotNull boolean isDeleted;

    protected JournalEntry() {}

    public JournalEntry(UUID operationId, Component component, long journalEntryId, String text, UUID createdBy) {
        this.operationId = operationId;
        this.component = component;
        this.journalEntryId = journalEntryId;
        this.text = text;
        this.createdBy = createdBy;
    }
}
