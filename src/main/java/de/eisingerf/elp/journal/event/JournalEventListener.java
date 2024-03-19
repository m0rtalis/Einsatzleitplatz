package de.eisingerf.elp.journal.event;

import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.journal.service.JournalTypeService;
import de.eisingerf.elp.shared.operation.event.OperationCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@org.springframework.stereotype.Component
public class JournalEventListener {

    private final JournalService journalService;
    private final JournalTypeService journalTypeService;

    @Autowired
    JournalEventListener(JournalService journalService, JournalTypeService journalTypeService) {
        this.journalService = journalService;
        this.journalTypeService = journalTypeService;
    }

    @EventListener
    public void handleOperationCreatedEvent(OperationCreatedEvent event) {
        JournalType journalType = journalTypeService.findOrCreate("Operation");
        journalService.create(
                event.operationId(),
                Component.OPERATION,
                journalType,
                "Operation " + event.name() + " created"
                );
    }
}
