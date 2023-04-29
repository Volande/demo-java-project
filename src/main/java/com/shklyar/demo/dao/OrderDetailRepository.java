package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Order;
import com.shklyar.demo.entities.OrderDetails;
import com.shklyar.demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.AbstractList;
import java.util.ArrayList;

public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long>, JpaSpecificationExecutor<OrderDetails> {
    ArrayList<OrderDetails> findOrderDetailsByOrder(Order order);
}
