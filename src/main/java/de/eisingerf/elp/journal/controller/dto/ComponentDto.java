package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.journal.entity.Component;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JournalComponent")
public enum ComponentDto {
    JOURNAL,
    OPERATION;

    public static ComponentDto from(Component component) {
        return switch (component) {
            case JOURNAL -> ComponentDto.JOURNAL;
            case OPERATION -> ComponentDto.OPERATION;
        };
    }
}
