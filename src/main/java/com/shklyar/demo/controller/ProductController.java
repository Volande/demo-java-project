package com.shklyar.demo.controller;

import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController
{
   ProductService productService;
   ProductRepository productRepository;

   @Autowired
   public ProductController(ProductService productService,ProductRepository productRepository)
   {
      this.productService = productService;
      this.productRepository = productRepository;
   }


   @GetMapping(value = "/clothes/{id}")
   public ResponseEntity<Product> findAllProducts(@PathVariable(name = "id") Long id)
   {
      Product product = productRepository.findProductById(id);
      return new ResponseEntity<>(product, HttpStatus.OK);
   }

   @GetMapping("/")
   public @ResponseBody
   ResponseEntity<List<Product>> findAllProducts()
   {
      return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
   }

   @GetMapping("/filter")
   @ResponseBody
   public ResponseEntity<List<Product>> findProduct(@RequestBody  String title){
      List<Product> product = productService.findProduct(title);
      return new ResponseEntity<>(product, HttpStatus.OK);
   }


   @PostMapping("/save")
   public ResponseEntity<Product> saveUser(Product product)
   {
      return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.OK);
   }

}
