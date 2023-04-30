package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Customer;
import com.shklyar.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    ArrayList<Order> findOrderByCustomer(Customer customer);
}
