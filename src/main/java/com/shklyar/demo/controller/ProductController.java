package com.shklyar.demo.controller;

import com.shklyar.demo.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController
{

   @GetMapping("/getAll")
   public @ResponseBody
   ResponseEntity<List<Product>> getAllProducts() {
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
   }

}
