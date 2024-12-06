package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "journal_reference")
@Table
@EqualsAndHashCode()
@Getter
public class JournalEntryReference {

	@Id
	private final UUID id = IdGenerator.generate();

	@OneToOne
	@JoinColumn(name = "REFERENCED_ENTRY")
	private JournalEntry referencedEntry;

	@Column
	@CreatedBy
	private UUID userId;

	@CreatedDate
	private Instant createdAt;

	@Column(name = "TYPE")
	private ReferenceType referenceType;

	protected JournalEntryReference() {}

	public JournalEntryReference(JournalEntry referencedEntry, ReferenceType referenceType) {
		this.referencedEntry = referencedEntry;
		this.referenceType = referenceType;
		this.createdAt = Instant.now();
	}
}
