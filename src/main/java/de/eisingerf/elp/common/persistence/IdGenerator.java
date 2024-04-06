package de.eisingerf.elp.common.persistence;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import java.util.UUID;

public final class IdGenerator {
    private static final NoArgGenerator generator = Generators.timeBasedEpochGenerator();

    public static UUID generate() {
        return generator.generate();
    }
}
