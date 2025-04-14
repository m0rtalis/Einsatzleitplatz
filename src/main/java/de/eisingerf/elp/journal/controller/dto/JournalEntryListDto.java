package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.common.api.rest.list.ListDto;
import de.eisingerf.elp.journal.entity.JournalEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

@Schema(name = "JournalEntryList")
public class JournalEntryListDto extends ListDto<JournalEntryDto> {

	@SuppressWarnings("unused")
	protected JournalEntryListDto() {
		// Jackson Default Constructor
		super();
	}

	public JournalEntryListDto(Page<JournalEntryDto> page) {
		super(page);
	}

	public static JournalEntryListDto from(Page<JournalEntry> journal) {
		Page<JournalEntryDto> journalEntryDtoPage = journal.map(
				JournalEntryDto::from);
		return new JournalEntryListDto(journalEntryDtoPage);
	}
}
