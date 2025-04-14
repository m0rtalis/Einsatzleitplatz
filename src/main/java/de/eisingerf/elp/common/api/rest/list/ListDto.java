package de.eisingerf.elp.common.api.rest.list;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(name = "List")
public abstract class ListDto<T> {
    @NotNull private final List<T> data;

    @NotNull private final Pagination pagination;

    protected ListDto() {
        this.data = new ArrayList<>();
        this.pagination = new Pagination(0, 50, 1, 0, 0);
    }

    public ListDto(List<T> data, Pagination pagination) {
        Assert.isTrue(data.size() <= pagination.limit, "Data list size must be equal or smaller pagination limit");
        this.data = data;
        this.pagination = pagination;
    }

    public ListDto(Page<T> page) {
        this(
                page.getContent(),
                new Pagination((int) page.getPageable().getOffset(), page.getSize(), page.getNumber()+1, (int)page.getTotalElements(), Math.max(1, page.getTotalPages())));
    }

    // FIXME: NotNull is required so apidocs as property required but it's redundant as these are primitives
    public record Pagination(@NotNull int offset, @NotNull int limit, @NotNull int currentPage, @NotNull int totalElements, @NotNull int totalPages) {
        public Pagination {
            Assert.isTrue(offset >= 0, "Offset must be positive");
            Assert.isTrue(limit > 0, "Limit must be positive");
            Assert.isTrue(currentPage > 0, "Current page must be positive");
            Assert.isTrue(totalElements >= 0, "TotalElements must be positive");
            Assert.isTrue(totalPages > 0, "TotalPages must be positive");
        }

    }
}
