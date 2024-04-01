package de.eisingerf.elp.user.event;

import de.eisingerf.elp.shared.user.UserSignedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    UserEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishUserSignedIn(UUID id, String username) {
        this.applicationEventPublisher.publishEvent(new UserSignedInEvent(id, username));
    }
}
