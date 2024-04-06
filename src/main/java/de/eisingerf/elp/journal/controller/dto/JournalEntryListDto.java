package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.common.api.rest.list.ListDto;
import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

@Schema(name = "JournalEntryList")
public class JournalEntryListDto extends ListDto<JournalEntryDto> {
    public JournalEntryListDto(Slice<JournalEntryDto> slice) {
        super(slice);
    }

    public static JournalEntryListDto from(Slice<JournalEntry> journal) {
        Slice<JournalEntryDto> journalEntryDtoSlice = journal.map(JournalEntryDto::from);
        return new JournalEntryListDto(journalEntryDtoSlice);
    }
}
