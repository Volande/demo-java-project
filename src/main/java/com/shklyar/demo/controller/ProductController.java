package com.shklyar.demo.controller;

import com.shklyar.demo.dao.*;
import com.shklyar.demo.entities.*;
import com.shklyar.demo.entities.Collection;
import com.shklyar.demo.service.CategoryService;
import com.shklyar.demo.service.CollectionService;
import com.shklyar.demo.service.ProductService;
import com.shklyar.demo.service.SizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    ProductService productService;


    CollectionService collectionService;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    CollectionRepository collectionRepository;

    SizeRepository sizeRepository;

    SizesService sizesService;
    CategoryService categoryService;
    ProductInformationRepository productInformationRepository;
    AvailabilityRepository availabilityRepository;


    @Autowired
    public ProductController(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            CollectionRepository collectionRepository,
            SizeRepository sizeRepository,
            ProductService productService,
            CollectionService collectionService,
            SizesService sizesService,
            CategoryService categoryService,
            ProductInformationRepository productInformationRepository,
            AvailabilityRepository availabilityRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
        this.collectionService = collectionService;
        this.sizeRepository = sizeRepository;
        this.sizesService = sizesService;
        this.categoryService = categoryService;
        this.productInformationRepository = productInformationRepository;
        this.availabilityRepository = availabilityRepository;
    }


    @GetMapping(value = "/clothes/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(name = "id") Long id) {
        Product product = productRepository.findProductById(id);


        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/")
    public @ResponseBody
    ResponseEntity<List<Product>> findAllProducts(@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        List<Product> productList = productRepository.findAll();


        productList=  this.productService.checkByLanguage(language,productList);

        return new ResponseEntity<>( productList,HttpStatus.OK);
    }

    @GetMapping("/categories")
    public @ResponseBody
    ResponseEntity<List<Category>> findAllCategory(){

        List<Category> categories = categoryRepository.findAll();


        return new ResponseEntity<>( categories,HttpStatus.OK);
    }

    @GetMapping("/collection")
    public @ResponseBody
    ResponseEntity<List<Collection>> findAllCollection() {
        List<Collection> collections=collectionRepository.findAll();

        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
    @GetMapping("/availability")
    public @ResponseBody
    ResponseEntity<List<Availability>> findAllAvailability(){
        List<Availability> availabilities=availabilityRepository.findAll();


        return new ResponseEntity<>(availabilities,HttpStatus.OK);
    }

    @GetMapping("/sizes")
    public @ResponseBody
    ResponseEntity<List<Size>> findAllSizes() {
        return new ResponseEntity<>(sizeRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping("/newCategory")
    public @ResponseBody
    ResponseEntity<Category> saveNewCategory(Category category) {
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/newCollection")
    public @ResponseBody
    ResponseEntity<Collection> saveNewCollection(Collection collection) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/newSize")
    public @ResponseBody
    ResponseEntity<Size> saveNewSizes(Size string) {
        return new ResponseEntity<>(sizesService.initSize(string.getTitle()), HttpStatus.OK);
    }


    @PostMapping("/filter")
    @ResponseBody
    public ResponseEntity<List<Product>> findProduct(@RequestBody String title) {
        List<Product> products = productService.findProduct(title);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> saveProduct(
            @RequestPart("clothes") Product product,
            @RequestPart("availabilityName") String availabilityName,
            @RequestPart("categoryName") List<String> categoryNames,
            @RequestPart("collectionName") String collectionName,
            @RequestPart(value = "image", required = false) List<MultipartFile> multipartFile) {


        productService.saveProductAndEnrollImage(product,availabilityName,categoryNames,collectionName, multipartFile);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Long> deleteProduct(@PathVariable long productId) {
        ResponseEntity<Long> response = ResponseEntity.badRequest().body(null);

        Product product = productRepository.findProductById(productId);

        if (product != null) {
            response = productService.deleteProduct(productId);
        }

        return response;
    }


}
