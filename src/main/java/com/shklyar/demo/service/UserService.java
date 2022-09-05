package com.shklyar.demo.service;

import com.shklyar.demo.dto.UserDTO;
import com.shklyar.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{  // security
    User save(UserDTO userDTO);
}
