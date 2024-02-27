package de.eisingerf.elp.common.location.entity;

import jakarta.persistence.*;

@Entity(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADDRESS")
    private Address address;

    @Column(name = "coordiantes", nullable = false)
    private Coordinates coordinates;
}
