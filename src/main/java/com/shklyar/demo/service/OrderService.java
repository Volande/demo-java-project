package com.shklyar.demo.service;

import com.shklyar.demo.dao.OrderRepository;
import com.shklyar.demo.dao.CustomerRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.dao.UserRepository;
import com.shklyar.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class OrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderService(ProductRepository productRepository,
                        OrderRepository orderRepository,
                        UserRepository userRepository,
                        CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.customerRepository =customerRepository;
    }

    public void saveOrder(User user, ArrayList<Order> orderDetailsArray,Customer customer) {

        User userOld = userRepository.findByUserId(user.getUserId());
        userOld.setFirstName(user.getFirstName());
        userOld.setLastName(user.getLastName());


        userRepository.save(userOld);
        Double wholeSum = 0.0;

        Customer orderNew = new Customer();
        orderNew.setUser(userOld);

        ArrayList<Order> orderDetailsArrayNew = new ArrayList<>();
        for (Order orderDetails : orderDetailsArray) {
            wholeSum += orderDetails.getProduct().getPrice();

            Product product = productRepository.findProductById(orderDetails.getProduct().getId());
            Order orderDetailsNew = new Order();
            orderDetailsNew.setProduct(product);
            orderDetailsNew.setPrice(orderDetails.getPrice());
            orderDetailsNew.setSize(orderDetails.getSize());
            orderDetailsNew.setAmount(orderDetails.getProduct().getQuantity());
            orderRepository.save(orderDetailsNew);

            orderDetailsArrayNew.add(orderDetailsNew);

        }
        orderNew.setOrders(orderDetailsArrayNew);
        orderNew.setSum(BigDecimal.valueOf(wholeSum));
        orderNew.setStatus(OrderStatus.NEW);
        customerRepository.save(orderNew);
    }
}
