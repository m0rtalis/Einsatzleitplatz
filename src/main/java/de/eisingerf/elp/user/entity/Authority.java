package de.eisingerf.elp.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity(name = "authorities")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Authority implements GrantedAuthority {
    @Id
    @Generated
    @ColumnDefault("uuid()")
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id;

    @ToString.Include
    @ManyToOne(targetEntity = UserAuthentication.class)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    @NotBlank
    private String username;

    @Column
    @ToString.Include
    @NotBlank
    private String authority;

}
