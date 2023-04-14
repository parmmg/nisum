package com.nisum.challenge.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "phones")
@EqualsAndHashCode(of = "id")
@ToString(of = "number")
@Builder
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne
    @NotNull
    private User user;

}
