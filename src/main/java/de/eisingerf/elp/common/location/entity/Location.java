package de.eisingerf.elp.common.location.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import de.eisingerf.elp.common.persistence.ReadOnly;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity(name = "location")
@EntityListeners(ReadOnly.class)
public class Location {

    @Id
    @GeneratedValue
    private UUID id = IdGenerator.generate();

    @Column
    private Address address;

    @Column
    @NotNull
    private Coordinates coordinates;
}
