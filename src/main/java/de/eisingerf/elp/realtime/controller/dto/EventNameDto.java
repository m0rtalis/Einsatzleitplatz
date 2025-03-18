package de.eisingerf.elp.realtime.controller.dto;

import de.eisingerf.elp.shared.realtime.EventName;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventName", enumAsRef = true)
public enum EventNameDto {
	CREATE_JOURNAL_ENTRY, UPDATE_JOURNAL_ENTRY, DELETE_JOURNAL_ENTRY;

	public static EventNameDto from(EventName eventName) {
		return switch (eventName) {
			case CREATE_JOURNAL_ENTRY -> EventNameDto.CREATE_JOURNAL_ENTRY;
			case UPDATE_JOURNAL_ENTRY -> EventNameDto.UPDATE_JOURNAL_ENTRY;
			case DELETE_JOURNAL_ENTRY -> EventNameDto.DELETE_JOURNAL_ENTRY;
		};
	}
}
