package com.shklyar.demo.config;

import com.shklyar.demo.dao.UserRepository;
import com.shklyar.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
class MyManager implements AuthenticationManager
{

   @Autowired
   public MyManager(UserRepository userRepository, PasswordEncoder passwordEncoder)
   {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   UserRepository userRepository;

   PasswordEncoder passwordEncoder;

   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException
   {
      User user = userRepository.findFirstByUsername((String) authentication.getPrincipal());

      if (user != null)
      {
         if (passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword()))
         {
            return authentication;
         }
      }

      throw new BadCredentialsException("Incorrect login or password");
   }
}