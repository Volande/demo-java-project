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
        this.customerRepository = customerRepository;
    }

    public void saveOrder(User user, ArrayList<OrderedProduct> ordersArray, Customer customer) {
        Double wholeSum = 0.0;


       if(user.getUserId() != null) {
            User userOld= userRepository.findByUserId(user.getUserId());
            userOld.setFirstName(customer.getFirstName());
            userOld.setLastName(customer.getLastName());
            userOld.setNumberPhone(customer.getNumberPhone());
            userOld.setPostOffice(customer.getPostOffice());
            userOld.setDepartmentPostOffice(customer.getDepartmentPostOffice());
            userRepository.save(userOld);
            customer.setUser(userOld);
        }

        ArrayList<OrderedProduct> ordersArrayNew = new ArrayList<>();
        for (OrderedProduct orderedProduct : ordersArray) {
            wholeSum += orderedProduct.getProduct().getPrice();

            Product product = productRepository.findProductById(orderedProduct.getProduct().getId());
            OrderedProduct ordersNew = new OrderedProduct();
            ordersNew.setProduct(product);
            ordersNew.setPrice(orderedProduct.getPrice());
            ordersNew.setSize(orderedProduct.getSize());
            ordersNew.setAmount(orderedProduct.getProduct().getQuantity());
            orderRepository.save(ordersNew);
            ordersArrayNew.add(ordersNew);

        }

        customer.setOrders(ordersArrayNew);
        customer.setSum(BigDecimal.valueOf(wholeSum));
        customer.setStatus(OrderStatus.NEW);

        customerRepository.save(customer);



























        /*User userOld = userRepository.findByUserId(user.getUserId());
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
        customerRepository.save(orderNew);*/
    }
}
