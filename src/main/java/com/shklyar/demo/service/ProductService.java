package com.shklyar.demo.service;

import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{

   @Autowired
   public ProductService(ProductRepository productRepository)
   {
      this.productRepository = productRepository;
   }

   ProductRepository productRepository;


   public Product saveProduct(Product product){
      return productRepository.save(product);
   }

   public List<Product> findAllProducts(){
      return productRepository.findAll();
   }
}
