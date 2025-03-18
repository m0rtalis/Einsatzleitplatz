package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.record.service.RecordService;
import de.eisingerf.elp.shared.realtime.Event;
import de.eisingerf.elp.shared.realtime.EventName;
import de.eisingerf.elp.shared.realtime.EventStream;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
@Transactional
public class JournalService {

	private final RecordService recordService;
	private final JournalRepository journalRepository;
	private final GetAuthenticatedUserId getAuthenticatedUserId;
	private final EventStream eventStream;


	@Autowired
	JournalService(RecordService recordService, JournalRepository journalRepository, GetAuthenticatedUserId getAuthenticatedUserId, EventStream eventStream) {
		this.recordService = recordService;
		this.journalRepository = journalRepository;
		this.getAuthenticatedUserId = getAuthenticatedUserId;
		this.eventStream = eventStream;
	}

	public Page<JournalEntry> getJournal(UUID operationId, Pageable pageable) {
		Page<JournalEntry> journal = journalRepository.findByOperationId(
				operationId,
				pageable);
		return journal;
	}

	public JournalEntry create(UUID operationId, String text) {
		Assert.hasText(text, "Text must not be empty");

		UUID userId = getAuthenticatedUserId.getAuthenticatedUserId();
		long journalEntryId = journalRepository.findNextJournalEntryId(
				operationId);
		var journalEntry = new JournalEntry(operationId,
											journalEntryId,
											text,
											userId);
		var savedEntry = this.journalRepository.save(journalEntry);

		recordService.record(operationId, "JournalEntry created: " + text);
		eventStream.send(new Event(EventName.CREATE_JOURNAL_ENTRY, savedEntry));

		return savedEntry;
	}

	public JournalEntry update(UUID id, String newText) {
		JournalEntry entry = this.get(id);
		if (entry.isDeleted()) {
			throw new RuntimeException();
		}

		entry.setText(newText);
		var updatedEntry = journalRepository.save(entry);

		eventStream.send(new Event(EventName.UPDATE_JOURNAL_ENTRY, updatedEntry));

		return updatedEntry;
	}

	public void delete(UUID id, @Nullable String text) {
		JournalEntry entry = this.get(id);
		if (entry.isDeleted()) {
			throw new RuntimeException();
		}
		entry.setDeleted(true);
		var deletedEntry = journalRepository.save(entry);
		if (text != null) {
			// TODO: I18N this and every other hardcoded string
			String deleteDescription = "Delete entry " + entry.getJournalEntryId();
			this.create(entry.getOperationId(),
						deleteDescription + "\n" + text);
		}

		eventStream.send(new Event(EventName.DELETE_JOURNAL_ENTRY, deletedEntry));
	}

	public JournalEntry get(UUID id) {
		return journalRepository.findById(id).orElseThrow();
	}
}
