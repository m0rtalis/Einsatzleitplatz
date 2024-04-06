package de.eisingerf.elp.common.location.entity;

import jakarta.persistence.Embeddable;

// https://geotools.org
// This location package could be it's own module
@Embeddable
public record Coordinates(double latitude, double longitude) {}
