package de.eisingerf.elp.user.persistence;

import de.eisingerf.elp.user.entity.UserAuthentication;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAuthentication, UUID> {

    Optional<UserAuthentication> findByUsername(String username);
}
