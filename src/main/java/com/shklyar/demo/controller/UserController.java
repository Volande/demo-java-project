package com.shklyar.demo.controller;

import com.shklyar.demo.dto.UserDTO;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newUser(Model model){
    model.addAttribute("user", new UserDTO());
    return "user";
    }

    @PostMapping("/new")
    public ResponseEntity<String> saveUser(UserDTO userDTO){

        return new ResponseEntity("OK TEST String", HttpStatus.OK);
    }
}
