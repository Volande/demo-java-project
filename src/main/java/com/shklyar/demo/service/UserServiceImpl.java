package com.shklyar.demo.service;

import com.shklyar.demo.dao.UserRepository;
import com.shklyar.demo.dto.AdminUserDTO;
import com.shklyar.demo.entities.Role;
import com.shklyar.demo.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User save(User user, boolean isAlreadyEncoded) {
        if(!isAlreadyEncoded){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User save(User user) {
        return save(user,false);
    }

    public User save(AdminUserDTO user) {
        return save(new User(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()));
    }

    @Override
    public User register(User user) {
        user.setRole(Role.CLIENT);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findFirstByUsername(username);
        log.info("IN findByUsername - user: {} found by username", result, username);
        return result;
    }

    @Override
    public User findById(Long userId) {
        User result = userRepository.findById(userId).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", userId);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findFirstByUsername(username);
    }
}
