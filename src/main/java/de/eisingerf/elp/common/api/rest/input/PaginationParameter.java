package de.eisingerf.elp.common.api.rest.input;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

public record PaginationParameter<S extends Enum<S>>(int offset, int limit) {
	public PaginationParameter {
		Assert.isTrue(offset >= 0, "Offset must be positive");
		Assert.isTrue(limit > 0, "Limit must be greater 0");
		Assert.isTrue(limit <= 150, "Limit must not be greater 150");
	}

	Pageable toPageable() {
		return null;
	}
}
