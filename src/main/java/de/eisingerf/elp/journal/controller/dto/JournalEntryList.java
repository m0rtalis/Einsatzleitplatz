package de.eisingerf.elp.journal.controller.dto;

import de.eisingerf.elp.common.api.rest.ListDto;
import de.eisingerf.elp.journal.entity.JournalEntry;

import java.util.List;

public class JournalEntryList extends ListDto<JournalEntry> {
	public JournalEntryList(List<JournalEntry> data, Pagination pagination) {
		super(data, pagination);
	}
}
