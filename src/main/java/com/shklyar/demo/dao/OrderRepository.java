package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Customer;
import com.shklyar.demo.entities.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;

public interface OrderRepository extends JpaRepository<OrderedProduct, Long>, JpaSpecificationExecutor<OrderedProduct> {
    ArrayList<OrderedProduct> findOrdersByCustomer(Customer customer);
}
