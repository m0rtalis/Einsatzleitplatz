package de.eisingerf.elp.shared.realtime;

import jakarta.annotation.Nullable;

import java.util.UUID;

public record Event(UUID id, EventName name, Object data, @Nullable String comment) {
	public Event(UUID id, EventName name, Object data) {
		this(id, name, data, null);
	}
}
