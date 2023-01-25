package com.shklyar.demo.controller;

import com.shklyar.demo.dto.ClientUserDTO;
import com.shklyar.demo.entities.User;
import com.shklyar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/newUser")
    public ResponseEntity<String> saveUser(ClientUserDTO userDTO){
        User user = userService.save(userDTO);
        return new ResponseEntity(user.getUserId(), HttpStatus.OK);
    }


}
