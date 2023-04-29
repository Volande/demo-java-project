package com.shklyar.demo.service;

import com.shklyar.demo.dao.OrderDetailRepository;
import com.shklyar.demo.dao.OrderRepository;
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
    private OrderDetailRepository orderDetailRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderService(ProductRepository productRepository,
                        OrderDetailRepository orderDetailRepository,
                        UserRepository userRepository,
                        OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.orderRepository=orderRepository;
    }

    public void saveOrder(User user, ArrayList<OrderDetails> orderDetailsArray) {

        User userOld = userRepository.findByUserId(user.getUserId());
        userOld.setFirstName(user.getFirstName());
        userOld.setLastName(user.getLastName());
        userOld.setNumberPhone(user.getNumberPhone());
        userOld.setDepartmentPostOffice(user.getDepartmentPostOffice());
        userOld.setPostOffice(user.getPostOffice());
        userRepository.save(userOld);

        Double wholeSum = 0.0;

        Order orderNew = new Order();
        orderNew.setUser(userOld);

        ArrayList<OrderDetails> orderDetailsArrayNew = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsArray) {
            wholeSum += orderDetails.getProduct().getPrice();

            Product product = productRepository.findProductById(orderDetails.getProduct().getId());
            OrderDetails orderDetailsNew = new OrderDetails();
            orderDetailsNew.setProduct(product);
            orderDetailsNew.setPrice(orderDetails.getPrice());
            orderDetailsNew.setSize(orderDetails.getSize());
            orderDetailsNew.setAmount(orderDetails.getProduct().getQuantity());
            orderDetailRepository.save(orderDetailsNew);

            orderDetailsArrayNew.add(orderDetailsNew);

        }
        orderNew.setDetails(orderDetailsArrayNew);
        orderNew.setSum(BigDecimal.valueOf(wholeSum));
        orderNew.setStatus(OrderStatus.NEW);
        orderRepository.save(orderNew);



    }


}
