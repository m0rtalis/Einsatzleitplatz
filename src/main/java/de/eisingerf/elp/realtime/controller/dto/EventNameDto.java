package de.eisingerf.elp.realtime.controller.dto;

import de.eisingerf.elp.shared.realtime.EventName;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventName", enumAsRef = true)
public enum EventNameDto {
   NEW_JOURNAL_ENTRY;

   public static EventNameDto from(EventName eventName) {
       return switch (eventName) {
           case NEW_JOURNAL_ENTRY -> EventNameDto.NEW_JOURNAL_ENTRY;
       };
   }
}
