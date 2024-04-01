package de.eisingerf.elp.shared.user;

import java.util.UUID;

public record UserSignedInEvent(UUID id, String username) {}
