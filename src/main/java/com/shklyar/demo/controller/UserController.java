package com.shklyar.demo.controller;

import com.shklyar.demo.dao.OrderDetailRepository;
import com.shklyar.demo.dao.OrderRepository;
import com.shklyar.demo.dto.ClientUserDTO;
import com.shklyar.demo.entities.Order;
import com.shklyar.demo.entities.OrderDetails;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.User;
import com.shklyar.demo.security.jwt.JwtTokenProvider;
import com.shklyar.demo.service.OrderService;
import com.shklyar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    private OrderService orderService;

    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserController(UserService userService,
                          OrderService orderService,
                          JwtTokenProvider jwtTokenProvider
    ) {
        this.userService = userService;
        this.orderService = orderService;
        this.jwtTokenProvider=jwtTokenProvider;
    }


    @PostMapping("/newUser")
    public ResponseEntity saveUser(ClientUserDTO userDTO) {
        User user = userService.save(userDTO);

        String token = jwtTokenProvider.createToken(user.getUsername(),user.getRole());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("role", user.getRole().getAuthority());
        response.put("token", token);
        response.put("userId",user.getUserId());
        return  ResponseEntity.ok(response);
    }

    @PostMapping(value = "/orderClothes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> orderClothes(
            @RequestPart("user") User user,
            @RequestPart("orderDetails") ArrayList<OrderDetails> orderDetails
    ) {

        orderService.saveOrder(user,orderDetails);


        return new ResponseEntity<>(HttpStatus.OK);
    }


}
