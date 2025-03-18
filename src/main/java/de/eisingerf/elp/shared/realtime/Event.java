package de.eisingerf.elp.shared.realtime;

import jakarta.annotation.Nullable;
import java.util.UUID;

// SUGG: Make extendable and abstract and create an Event with typed data for each use-case.
// 	This would also make it more expressive to have reduced data payload
public record Event(UUID id, EventName name, Object data, @Nullable String comment) {

    public Event(EventName name, Object data) {
        this(UUID.randomUUID(), name, data, null);
    }
}
