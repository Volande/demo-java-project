package com.shklyar.demo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shklyar.demo.entities.Role;
import com.shklyar.demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDTO {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public User toUser(){
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);

        return user;
    }

    public static AdminUserDTO fromUser(User user){
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setUserId(user.getUserId());
        adminUserDTO.setUsername(user.getUsername());
        adminUserDTO.setFirstName(user.getFirstName());
        adminUserDTO.setLastName(user.getLastName());
        adminUserDTO.setEmail(user.getEmail());
        adminUserDTO.setRole(user.getRole());

        return adminUserDTO;
    }
}
