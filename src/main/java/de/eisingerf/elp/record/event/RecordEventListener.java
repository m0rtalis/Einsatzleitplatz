package de.eisingerf.elp.record.event;

import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.shared.operation.OperationCreatedEvent;
import de.eisingerf.elp.shared.user.UserSignedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RecordEventListener {

    private final JournalService journalService;

    @Autowired
    RecordEventListener(JournalService journalService) {
        this.journalService = journalService;
    }

    @EventListener
    public void handleOperationCreatedEvent(OperationCreatedEvent event) {
        journalService.create(event.operationId(), "Operation " + event.name() + " created");
    }

    @EventListener
    public void handleUserSignedInEvent(UserSignedInEvent event) {
        //        journalService.create(null, Component.USER, "User " + event.username() + " signed in");
    }
}
