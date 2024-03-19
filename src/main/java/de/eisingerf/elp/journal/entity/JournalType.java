package de.eisingerf.elp.journal.entity;

import de.eisingerf.elp.common.persistence.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity(name = "journalType")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class JournalType {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    UUID id = IdGenerator.generate();

    @Column(name = "NAME", unique = true)
    @NotBlank
    @ToString.Include
    String name;

    protected JournalType() {}

    public JournalType(String name) {
        this.name = name;
    }

}
