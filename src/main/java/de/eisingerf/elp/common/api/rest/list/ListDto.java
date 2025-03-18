package de.eisingerf.elp.common.api.rest.list;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import org.springframework.util.Assert;

@Getter
@Schema(name = "List")
public abstract class ListDto<T> {
    @NotNull private final List<T> data;

    @NotNull private final Pagination pagination;

    protected ListDto() {
        this.data = new ArrayList<>();
        this.pagination = new Pagination(0, 50, 0);
    }

    public ListDto(List<T> data, Pagination pagination) {
        Assert.isTrue(data.size() <= pagination.limit, "Data list size must be equal or smaller pagination limit");
        this.data = data;
        this.pagination = pagination;
    }

    public ListDto(Slice<T> slice) {
        this(
                slice.getContent(),
                new Pagination((int) slice.getPageable().getOffset(), slice.getSize(), slice.getNumberOfElements()));
    }

    public record Pagination(int offset, int limit, int totalElements) {
        public Pagination {
            Assert.isTrue(offset >= 0, "Offset must be positive");
            Assert.isTrue(limit > 0, "Limit must be positive");
            Assert.isTrue(totalElements >= 0, "TotalElements must be positive");
        }

        public int getCurrentPage() {
            return offset / limit + 1;
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
