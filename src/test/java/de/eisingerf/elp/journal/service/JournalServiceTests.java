package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.record.service.RecordService;
import de.eisingerf.elp.shared.realtime.EventName;
import de.eisingerf.elp.shared.realtime.EventStream;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JournalServiceTests {

	JournalService journalService;

	@Mock
	RecordService recordService;

	@Mock
	JournalRepository journalRepository;

	@Mock
	GetAuthenticatedUserId getAuthenticatedUserId;

	@Mock
	EventStream eventStream;

	@BeforeEach
	void beforeEach() {
		journalService = new JournalService(recordService,
											journalRepository,
											getAuthenticatedUserId,
											eventStream);
	}

	@Nested
	public class CreateJournalEntry {

		@Test
		void shouldCreateEntry() {
			when(journalRepository.findNextJournalEntryId(any())).thenReturn(1L);
			when(journalRepository.save(any())).thenAnswer(i -> i.getArgument(0));
			when(getAuthenticatedUserId.getAuthenticatedUserId()).thenReturn(
					UUID.randomUUID());

			journalService.create(UUID.randomUUID(), "Test Journal");

			verify(journalRepository).save(any());
		}

		@Test
		void createShouldSendEventStream() {
			when(journalRepository.findNextJournalEntryId(any())).thenReturn(1L);
			when(journalRepository.save(any())).thenAnswer(i -> i.getArgument(0));
			when(getAuthenticatedUserId.getAuthenticatedUserId()).thenReturn(
					UUID.randomUUID());

			journalService.create(UUID.randomUUID(), "Test Journal");

			verify(eventStream).send(argThat(e -> e.name().equals(EventName.CREATE_JOURNAL_ENTRY)));
		}

		@Test
		void createEmptyTextShouldThrowException() {
			assertThrows(IllegalArgumentException.class,
						 () -> journalService.create(UUID.randomUUID(), " "));
		}

		@Test
		void createShouldCreateRecordEntry() {}
	}

	@Nested
	public class DeleteJournalEntry {

		@BeforeEach
		void beforeEach() {
			var entry = new JournalEntry(UUID.randomUUID(),
										 1L,
										 "JournalEntry",
										 UUID.randomUUID());
			when(journalRepository.findById(any())).thenReturn(Optional.of(entry));
		}

		@Test
		void deleteShouldSetJournalEntryToDeleted() {
			journalService.delete(UUID.randomUUID(), "Delete Journal Entry");
			fail();
		}

		@Test
		void deleteShouldCreateAReference() {
		}

		@Test
		void deletingEntryTwiceShouldThrowException() {
		}

		@Test
		void deleteShouldCreateRecordEntry() {

		}
	}
}
