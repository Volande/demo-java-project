package com.shklyar.demo.rest;

import com.shklyar.demo.entities.User;
import com.shklyar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") Long userId){
        User user = userService.findById(userId);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
