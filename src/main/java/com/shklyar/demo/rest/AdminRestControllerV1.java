package com.shklyar.demo.rest;

import com.shklyar.demo.dao.OrderDetailRepository;
import com.shklyar.demo.dao.OrderRepository;
import com.shklyar.demo.entities.Order;
import com.shklyar.demo.entities.User;
import com.shklyar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    @Autowired
    private UserService userService;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    @GetMapping(value = "users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") Long userId) {
        User user = userService.findById(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "orderAll")
    public ResponseEntity<List<Order>> getOrderAll() {
        List<Order> orderArrayList = orderRepository.findAll();

        return new ResponseEntity<>(orderArrayList, HttpStatus.OK);
    }
}
