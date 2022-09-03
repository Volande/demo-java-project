package com.shklyar.demo.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @NonNull
    @Size(max = 20)
    private String name;

    @NonNull
    @Size(max = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Backet backet;
}
