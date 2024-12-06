package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.persistence.JournalRepository;
import de.eisingerf.elp.shared.realtime.EventName;
import de.eisingerf.elp.shared.realtime.EventStream;
import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JournalServiceTests {
    JournalService journalService;

    @Mock
    JournalRepository journalRepository;

    @Mock
    GetAuthenticatedUserId getAuthenticatedUserId;

    @Mock
    EventStream eventStream;

    @BeforeEach
    void beforeEach() {
        journalService = new JournalService(journalRepository, getAuthenticatedUserId, eventStream);
    }

    @Test
    void ShouldCreateEntry() {
        when(journalRepository.findNextJournalEntryId(any())).thenReturn(1L);
        when(journalRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(getAuthenticatedUserId.getAuthenticatedUserId()).thenReturn(UUID.randomUUID());

        journalService.create(UUID.randomUUID(), Component.JOURNAL, "Test Journal");

        verify(journalRepository).save(any());
    }

    @Test
    void CreateShouldSendEventStream() {
        when(journalRepository.findNextJournalEntryId(any())).thenReturn(1L);
        when(journalRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(getAuthenticatedUserId.getAuthenticatedUserId()).thenReturn(UUID.randomUUID());

        journalService.create(UUID.randomUUID(), Component.JOURNAL, "Test Journal");

        verify(eventStream).send(argThat(e -> e.name().equals(EventName.NEW_JOURNAL_ENTRY)));
    }

    @Test
    void CreateEmptyTextShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class, () -> journalService.create(UUID.randomUUID(), Component.JOURNAL, " "));
    }
}
