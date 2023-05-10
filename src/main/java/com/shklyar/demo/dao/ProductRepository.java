package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Images;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.ProductInformation;
import com.shklyar.demo.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    public Product findProductById(Long id) ;

    public List<Product> findProductByProductInformation(String language);





}


