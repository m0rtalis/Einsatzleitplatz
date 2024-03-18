package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity(name = "journalComponent")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class JournalComponent {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id = IdGenerator.generate();

    @ToString.Include
    private String component;

    protected JournalComponent() {}

    public JournalComponent(String component) {
        this.component = component;
    }
}
