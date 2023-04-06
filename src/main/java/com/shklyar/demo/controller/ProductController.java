package com.shklyar.demo.controller;

import com.shklyar.demo.dao.CategoryRepository;
import com.shklyar.demo.dao.CollectionRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.dao.SizesRepository;
import com.shklyar.demo.entities.*;
import com.shklyar.demo.service.CategoryService;
import com.shklyar.demo.service.CollectionService;
import com.shklyar.demo.service.ProductService;
import com.shklyar.demo.service.SizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    SizesRepository sizesRepository;

    SizesService sizesService;
    CategoryService categoryService;


    @Autowired
    public ProductController(ProductService productService,
                             ProductRepository productRepository,
                             CategoryRepository categoryRepository,
                             CollectionRepository collectionRepository,
                             CollectionService collectionService,
                             SizesRepository sizesRepository,
                             SizesService sizesService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionRepository=collectionRepository;
        this.collectionService=collectionService;
        this.sizesRepository=sizesRepository;
        this.sizesService=sizesService;
        this.categoryService=categoryService;
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

    @GetMapping("/sizes")
    public @ResponseBody
    ResponseEntity<List<Sizes>> findAllSizes() {
        return new ResponseEntity<>(sizesRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping( "/newCategory")
    public @ResponseBody
    ResponseEntity<Category> saveNewCategory( Category category) {
        return new ResponseEntity<>(categoryService.initCategory(category.getTitle()), HttpStatus.OK);
    }

    @PostMapping("/newCollection")
    public @ResponseBody
    ResponseEntity<Collection> saveNewCollection( Collection collection) {
        return new ResponseEntity<>(collectionService.initCollection(collection.getTitle()), HttpStatus.OK);
    }

    @PostMapping("/newSize")
    public @ResponseBody
    ResponseEntity<Sizes> saveNewSizes(Sizes string ) {
        return new ResponseEntity<>(sizesService.initSize(string.getTitle()), HttpStatus.OK);
    }


    @GetMapping("/filter")
    @ResponseBody
    public ResponseEntity<List<Product>> findProduct(@RequestBody String title) {
        List<Product> product = productService.findProduct(title);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> saveProduct(
            @RequestPart("clothes") Product product,
            @RequestPart(value = "image", required = false)List<MultipartFile>   multipartFile) {


        productService.saveProductAndEnrollImage(product,multipartFile);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteCar(@PathVariable long productId) {
        ResponseEntity<Product> response = ResponseEntity.badRequest().body(null);

        Product product = productRepository.findProductById(productId);

        if (product != null) {
            response = productService.deleteProduct(productId);
        }

        return response;
    }
}
