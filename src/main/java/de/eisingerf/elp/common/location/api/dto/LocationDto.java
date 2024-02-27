package de.eisingerf.elp.common.location.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Location")
public record LocationDto(@NotBlank double longitude, @NotBlank double latitude) {}
