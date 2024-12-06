package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

// TBF I don't like Hibernate as it is too inflexible and
// causes too much data being requested from the database.
// Using it here to practice it. Would probably look into just using JDBCClient or similar-
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

    @Column(name = "JOURNAL_ENTRY_ID", updatable = false, nullable = false)
    @Positive
    @ToString.Include
    @NaturalId
    private long journalEntryId;

    @Column(updatable = false, nullable = false)
    @ToString.Include
    @NotNull
    @NaturalId
    private UUID operationId;

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Component component;

    @Column(name = "TEXT")
    @Lob
    @NotNull
    private String text;

    //    @ManyToOne(targetEntity = UserDetail.class)
    //    @JoinColumn(name = "CREATED_BY")
    @Column(updatable = false, nullable = false) // TODO: Create foreign key, for others as well
    @NotNull
    @CreatedBy // TODO: Should be populated automatically
    private UUID createdBy;

    @Column(name = "CREATED_AT", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "IS_DELETED", nullable = false)
    @Setter
    private boolean isDeleted;

    // https://softwarecave.org/2018/02/11/mapping-collection-of-simple-type-in-jpa-using-elementcollection/
    // https://medium.com/codex/element-collection-vs-one-to-many-in-jpa-andhibernate-e4ae83642d99
    @OneToMany
    @JoinColumn(name = "\"REFERENCE\"")
    private final List<JournalEntryReference> references = new ArrayList<>();

    protected JournalEntry() {}

    public JournalEntry(UUID operationId, Component component, long journalEntryId, String text, UUID createdBy) {
        this.operationId = operationId;
        this.component = component;
        this.journalEntryId = journalEntryId;
        this.text = text;
        this.createdBy = createdBy;
        this.createdAt = Instant.now();
    }

    public void addReference(JournalEntry referencedEntry, ReferenceType type) {
        var reference = new JournalEntryReference(referencedEntry, ReferenceType.LINK);
        this.references.add(reference);
    }
}
