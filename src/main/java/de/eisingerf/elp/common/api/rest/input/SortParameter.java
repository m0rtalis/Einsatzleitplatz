package de.eisingerf.elp.common.api.rest.input;

import org.springframework.data.domain.Sort;

public record SortParameter<T extends Enum<T>>(T field, Sort.Direction direction) {
	public SortParameter(T field) {
		this(field, Sort.Direction.ASC);
	}
}
