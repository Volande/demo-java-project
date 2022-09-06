package com.shklyar.demo.controller;

import com.shklyar.demo.entities.Product;
import com.shklyar.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController
{
   ProductService productService;

   @Autowired
   public ProductController(ProductService productService)
   {
      this.productService = productService;
   }

   @GetMapping("/findAll")
   public @ResponseBody
   ResponseEntity<List<Product>> findAllProducts()
   {
      return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
   }


   @PostMapping("/save")
   public ResponseEntity<Product> saveUser(Product product)
   {
      return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.OK);
   }
}
