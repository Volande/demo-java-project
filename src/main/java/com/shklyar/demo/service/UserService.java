package com.shklyar.demo.service;

import com.shklyar.demo.dto.AdminUserDTO;
import com.shklyar.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{  // security
    User save(AdminUserDTO userDTO);
    User register(User user);
    User findByUsername(String username);

    User findById(Long userId);


}
