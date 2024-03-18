package de.eisingerf.elp.journal.service;

import de.eisingerf.elp.journal.entity.JournalType;
import de.eisingerf.elp.shared.operation.event.OperationCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
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
                JournalComponentService.OPERATION,
                journalType,
                "Operation " + event.name() + " created",
                event.createdBy());
    }
}
