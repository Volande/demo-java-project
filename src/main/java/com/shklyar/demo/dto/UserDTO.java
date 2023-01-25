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
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);


        return user;
    }

    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());


        return userDTO;
    }
}
