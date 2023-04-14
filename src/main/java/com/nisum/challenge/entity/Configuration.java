package com.nisum.challenge.entity;

import com.nisum.challenge.config.enumerator.ConfigurationNameEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "configurations")
@EqualsAndHashCode(of = "id")
@ToString(of = "name")
@Builder
@AllArgsConstructor
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ConfigurationNameEnum name;
    @NotNull(message = "message required")
    @Builder.Default
    private String message = "Configuration error";
    @NotNull
    private String pattern;
}
