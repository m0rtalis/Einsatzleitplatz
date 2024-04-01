package de.eisingerf.elp.journal.event;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.shared.operation.OperationCreatedEvent;
import de.eisingerf.elp.shared.user.UserSignedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@org.springframework.stereotype.Component
public class JournalEventListener {

    private final JournalService journalService;

    @Autowired
    JournalEventListener(JournalService journalService) {
        this.journalService = journalService;
    }

    @EventListener
    public void handleOperationCreatedEvent(OperationCreatedEvent event) {
        journalService.create(
                event.operationId(),
                Component.OPERATION,
                "Operation " + event.name() + " created"
                );
    }

    @EventListener
    public void handleUserSignedInEvent(UserSignedInEvent event) {
//        journalService.create(null, Component.USER, "User " + event.username() + " signed in");
    }
}
