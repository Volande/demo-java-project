package com.shklyar.demo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shklyar.demo.entities.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientUserDTO {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /*
    public User toUser(){
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAuthorities(new ArrayList<Role>(role));


        return user;
    }*/
/*
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
    */
}
