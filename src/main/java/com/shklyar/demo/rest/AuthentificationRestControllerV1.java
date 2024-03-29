package com.shklyar.demo.rest;

import com.shklyar.demo.dao.UserRepository;
import com.shklyar.demo.entities.User;
import com.shklyar.demo.security.jwt.JwtTokenProvider;
import com.shklyar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequestMapping(value = "/api/v1/auth/")
@RestController
public class AuthentificationRestControllerV1 implements AuthenticationManager{
    private final JwtTokenProvider jwtTokenProvider;

    private  final UserService userService;

    @Autowired
    public AuthentificationRestControllerV1(JwtTokenProvider jwtTokenProvider, UserService userService,UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;
    
    @PostMapping("login")
    public ResponseEntity login(@RequestParam("username") String username,
                                @RequestParam("password") String password)
    {
        try{
            authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.findByUsername(username);

            if(user == null){
                throw new UsernameNotFoundException("User with username: " + username + "not found");
            }

            String token = jwtTokenProvider.createToken(username,user.getRole());
            String role = user.getRole().getAuthority();
            Long userId=user.getUserId();

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("role", role);
            response.put("jwt", token);
            response.put("userId",userId);

            return ResponseEntity.ok(response);

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }


    }

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
