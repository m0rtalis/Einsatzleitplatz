package de.eisingerf.elp.operation.entity;

import de.eisingerf.elp.common.location.entity.Location;
import de.eisingerf.elp.common.persistence.IdGenerator;
import de.eisingerf.elp.shared.patient.HasPatients;
import de.eisingerf.elp.shared.resource.HasResources;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

// Einsatz
@Entity(name = "operation")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Operation implements HasResources, HasPatients {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id = IdGenerator.generate();

    @Column(name = "NAME", unique = true)
    @NotBlank
    @ToString.Include
    private String name;

    @OneToOne
    private Location location;

    @OneToMany
    @OrderBy("name ASC")
    private List<Section> sections; // Einsatzabschnitte

    protected Operation() {}

    public Operation(String name) {
        this.name = name;
    }

}
