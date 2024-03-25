package de.eisingerf.elp.common.api.rest.list.input;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Sure Spring already has all of this, but I want to do it myself :)
// TODO:
@ParameterObject
@Getter
@Setter
public final class PaginationParameter {

    @Parameter(schema = @Schema(defaultValue = "0", minimum = "0"))
    @Min(0)
    private Integer offset;

    @Parameter(schema = @Schema(defaultValue = "50", minimum = "1", maximum = "200"))
    @Min(1)
    @Max(200)
    private Integer limit;

    @Nullable
    private org.springdoc.core.converters.models.Sort sort;

    public PaginationParameter(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "50") Integer limit,
            org.springdoc.core.converters.models.Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public Pageable toPageable() {
        int pageNumber = offset / limit;
        return sort == null ? PageRequest.of(pageNumber, limit) : PageRequest.of(pageNumber, limit, convertSort(sort));
    }

    @Nonnull
    private Sort convertSort(org.springdoc.core.converters.models.Sort sort) {
        List<Sort.Order> orders = sort.getSort().stream()
                .map(s -> {
                    String[] parts = s.split(",", 1);
                    String property = parts[0].trim();
                    Sort.Direction direction =
                            parts.length == 2 ? Sort.Direction.fromString(parts[1].trim()) : Sort.Direction.DESC;
                    return new Sort.Order(direction, property);
                })
                .toList();

        return Sort.by(orders);
    }
}
