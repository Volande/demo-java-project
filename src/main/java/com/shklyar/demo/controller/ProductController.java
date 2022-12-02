package com.shklyar.demo.controller;

import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.ProductFilter;
import com.shklyar.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController
{
   ProductService productService;
   ProductFilter productFilter;

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

   @GetMapping("/filter")
   @ResponseBody
   public ResponseEntity<List<Product>> findProduct(@RequestParam  String title){
      List<Product> product = productService.findProduct(title);
      return new ResponseEntity<>(product, HttpStatus.OK);
   }


   @PostMapping("/save")
   public ResponseEntity<Product> saveUser(Product product)
   {
      return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.OK);
   }

}
