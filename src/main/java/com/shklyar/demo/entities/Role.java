package com.shklyar.demo.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_name", length = 50)
    private String role;


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
