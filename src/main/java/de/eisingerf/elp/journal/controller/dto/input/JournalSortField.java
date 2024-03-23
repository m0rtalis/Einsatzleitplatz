package de.eisingerf.elp.journal.controller.dto.input;

import de.eisingerf.elp.common.api.rest.input.SortDirection;
import de.eisingerf.elp.common.api.rest.input.SortField;

public enum JournalSortField implements SortField {
	ID("id");

	private final String field;

	JournalSortField(String field) {
		this.field = field;
	}
	@Override
	public String getField() {
		return field;
	}

	@Override
	public SortDirection getDirection() {
		return null;
	}

}
