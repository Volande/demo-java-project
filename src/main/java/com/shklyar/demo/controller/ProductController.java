package com.shklyar.demo.controller;

import com.shklyar.demo.dao.CategoryRepository;
import com.shklyar.demo.dao.CollectionRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Category;
import com.shklyar.demo.entities.Collection;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.Sizes;
import com.shklyar.demo.service.CollectionService;
import com.shklyar.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

@Controller
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    ProductService productService;

    CollectionService collectionService;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    CollectionRepository collectionRepository;

    @Autowired
    public ProductController(ProductService productService,
                             ProductRepository productRepository,
                             CategoryRepository categoryRepository,
                             CollectionRepository collectionRepository,
                             CollectionService collectionService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionRepository=collectionRepository;
        this.collectionService=collectionService;
    }


    @GetMapping(value = "/clothes/{id}")
    public ResponseEntity<Product> findAllProducts(@PathVariable(name = "id") Long id) {
        Product product = productRepository.findProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/")
    public @ResponseBody
    ResponseEntity<List<Product>> findAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public @ResponseBody
    ResponseEntity<List<Category>> findAllCategory() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/collection")
    public @ResponseBody
    ResponseEntity<List<Collection>> findAllCollection() {
        return new ResponseEntity<>(collectionRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/filter")
    @ResponseBody
    public ResponseEntity<List<Product>> findProduct(@RequestBody String title) {
        List<Product> product = productService.findProduct(title);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> saveProduct(
            @RequestPart("clothes") Product product) {
        return new ResponseEntity<Product>(
                productService.saveProduct(product),
                HttpStatus.OK);
    }

}
