package de.eisingerf.elp.common.persistence;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

import java.util.UUID;

public class IdGenerator {
    private final static NoArgGenerator generator  = Generators.timeBasedEpochGenerator();

    public static UUID generate() {
        return generator.generate();
    }
}
