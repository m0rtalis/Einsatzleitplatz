package de.eisingerf.elp.operation.entity;

import de.eisingerf.elp.common.location.entity.Location;
import de.eisingerf.elp.shared.patient.HasPatients;
import de.eisingerf.elp.shared.resource.HasResources;
import jakarta.persistence.*;

import java.util.List;

// Einsatz
@Entity(name = "operation")
public class Operation implements HasResources, HasPatients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
    @OneToOne
    private Location location;
    @OneToMany
    @OrderBy("priority ASC")
    private List<Section> sections; // Einsatzabschnitte

    protected Operation() {}

    public Operation(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
