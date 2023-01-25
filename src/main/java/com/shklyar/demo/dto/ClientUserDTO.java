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

}
