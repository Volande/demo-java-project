package com.shklyar.demo.service;

import com.shklyar.demo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{  // security
    boolean save(UserDTO userDTO);
}
