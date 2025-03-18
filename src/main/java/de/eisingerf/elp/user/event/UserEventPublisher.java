package de.eisingerf.elp.user.event;

import de.eisingerf.elp.shared.user.UserSignedInEvent;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    UserEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishUserSignedIn(UUID userId, String username) {
        this.applicationEventPublisher.publishEvent(new UserSignedInEvent(userId, username));
    }
}
