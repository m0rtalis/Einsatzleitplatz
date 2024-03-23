package de.eisingerf.elp.common.api.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;
import org.springframework.util.Assert;

import java.util.List;

@Schema(name = "List")
public record ListDto<T>(List<T> data, Pagination pagination) {
	public static <T> ListDto<T> fromSlice(Slice<T> slice) {
		var pagination = new Pagination((int) slice.getPageable().getOffset(), slice.getSize(), slice.getNumberOfElements());
		return new ListDto<>(slice.getContent(), pagination);
	}

	public static class Pagination {
		private final int totalElements;
		private final int limit;
		private final int offset;

		public Pagination(int offset, int limit, int totalElements) {
			Assert.isTrue(offset >= 0, "Offset must be positive");
			Assert.isTrue(limit > 0, "Limit must be positive");
			Assert.isTrue(totalElements >= 0, "TotalElements must be positive");

			this.offset = offset;
			this.limit = limit;
			this.totalElements = totalElements;
		}

		public int getCurrentPage() {
			return offset / limit;
		}

		public int getTotalPages() {
			return Math.ceilDiv(totalElements, limit);
		}

		@SuppressWarnings("unused")
		public boolean isLastPage() {
			return getCurrentPage() == getTotalPages();
		}
	}
}
