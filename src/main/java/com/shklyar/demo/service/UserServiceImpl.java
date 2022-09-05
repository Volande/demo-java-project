package com.shklyar.demo.service;

import com.shklyar.demo.dao.UserRepository;
import com.shklyar.demo.dto.UserDTO;
import com.shklyar.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    public User save(UserDTO user) {
        return save(new User(user.getUsername(),user.getPassword()));
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findFirstByUsername(username);
    }
}
