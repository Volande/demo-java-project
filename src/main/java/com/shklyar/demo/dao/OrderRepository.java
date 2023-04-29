package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Collection;
import com.shklyar.demo.entities.Order;
import com.shklyar.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Order findOrderByUser(User user);
}
