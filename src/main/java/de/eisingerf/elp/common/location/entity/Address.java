package de.eisingerf.elp.common.location.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(String street, String houseNumber, String zip, String city) {
}
