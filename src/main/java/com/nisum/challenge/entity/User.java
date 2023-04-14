package com.nisum.challenge.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Builder
@AllArgsConstructor
public class User {
    @Id
    private UUID id;
    @NotNull(message = "Name required")
    private String name;
    @Column(unique = true)
    @NotNull(message = "Email required")
    private String email;
    @NotNull(message = "Password required")
    private String password;
    @NotNull(message = "Token required")
    private String token;
    @NotNull
    private Date created;
    @NotNull
    private Date modified;
    @NotNull
    private Date lastLogin;
    @NotNull
    @Builder.Default
    private Boolean active = true;
    @OneToMany(mappedBy = "user")
    private Set<Phone> phones;
}
