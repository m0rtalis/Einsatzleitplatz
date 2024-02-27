package de.eisingerf.elp.common.location.entity;

import jakarta.persistence.Embeddable;

// https://geotools.org
@Embeddable
public record Coordinates(double latitude, double longitude) {
}
