package com.shklyar.demo.service;

import com.shklyar.demo.dao.RoleRepository;
import com.shklyar.demo.dao.UserRepository;
import com.shklyar.demo.dto.ClientUserDTO;
import com.shklyar.demo.entities.Role;
import com.shklyar.demo.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository= roleRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    private UserRepository userRepository;

    private RoleRepository roleRepository;
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



       public User save(ClientUserDTO user) {
          return save(new User(
                  user.getUserId(),
                  user.getUsername(),
                  user.getFirstName(),
                  user.getLastName(),
                  user.getEmail(),
                  user.getPassword(),
                  roleRepository.findByRole("USER")));
       }

       private List<Role> toRolesObjects(List<String> stringRoles){


          return null;

       }



    public User register(User user) {
        return save(user,false);
    }


    public User findByUsername(String username) {
        User result = userRepository.findFirstByUsername(username);
        log.info("IN findByUsername - user: {} found by username", result, username);
        return result;
    }



    public User findById(Long userId) {
        User result = userRepository.findById(userId).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", userId);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findFirstByUsername(username);
    }
}
