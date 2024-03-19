package de.eisingerf.elp.operation.entity;

import de.eisingerf.elp.common.location.entity.Location;
import de.eisingerf.elp.common.persistence.IdGenerator;
import de.eisingerf.elp.shared.patient.HasPatients;
import de.eisingerf.elp.shared.resource.HasResources;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

// Einsatzabschnitt
@Entity(name = "operation_section")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Section implements HasResources, HasPatients {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id = IdGenerator.generate();

    @Column(name = "NAME")
    @NotBlank
    @ToString.Include
    private String name;

    @Column(name = "SECTION_LEADER")
    private String sectionLeader; // Abschnittsleiter

    @OneToOne
    private Location location;
}
