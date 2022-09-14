package com.shklyar.demo.dto;


import com.shklyar.demo.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
