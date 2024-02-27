package de.eisingerf.elp.operation.entity;

import de.eisingerf.elp.common.location.entity.Location;
import de.eisingerf.elp.shared.patient.HasPatients;
import de.eisingerf.elp.shared.resource.HasResources;
import jakarta.persistence.*;

// Einsatzabschnitt
@Entity(name = "operation_section")
public class Section implements HasResources, HasPatients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SECTION_LEADER")
    private String sectionLeader; // Abschnittsleiter

    @OneToOne
    Location location;

    @Column(name = "priority", nullable = false)
    int priority = Integer.MAX_VALUE;
}
