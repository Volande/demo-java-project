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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
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

    SizesRepository sizesRepository;

    SizesService sizesService;
    CategoryService categoryService;
    ProductInformationRepository productInformationRepository;


    @Autowired
    public ProductController(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            CollectionRepository collectionRepository,
            SizesRepository sizesRepository,
            ProductService productService,
            CollectionService collectionService,
            SizesService sizesService,
            CategoryService categoryService,
            ProductInformationRepository productInformationRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
        this.collectionService = collectionService;
        this.sizesRepository = sizesRepository;
        this.sizesService = sizesService;
        this.categoryService = categoryService;
        this.productInformationRepository = productInformationRepository;
    }


    @GetMapping(value = "/clothes/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(name = "id") Long id,
                                                   @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        Product product = productRepository.findProductById(id);
        List<ProductInformation> productInformationList = new ArrayList<ProductInformation>() ;

        for (ProductInformation productInformation:product.getProductInformation()){
            if(productInformation.getLanguage().equals(language)){

                productInformationList.add(productInformation);
            }
        }
        product.setProductInformation(productInformationList);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/")
    public @ResponseBody
    ResponseEntity<List<Product>> findAllProducts(@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        List<Product> productList = productRepository.findAll();



        for(Product product:productList){
            List<ProductInformation> productInformationList = new ArrayList<ProductInformation>() ;

            for (ProductInformation productInformation:product.getProductInformation()){
                if(productInformation.getLanguage().equals(language)){

                    productInformationList.add(productInformation);
                }
            }
            product.setProductInformation(productInformationList);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
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

    @PostMapping("/newCategory")
    public @ResponseBody
    ResponseEntity<Category> saveNewCategory(Category category) {
        return new ResponseEntity<>(categoryService.initCategory(category.getTitle()), HttpStatus.OK);
    }

    @PostMapping("/newCollection")
    public @ResponseBody
    ResponseEntity<Collection> saveNewCollection(Collection collection) {
        return new ResponseEntity<>(collectionService.initCollection(collection.getTitle()), HttpStatus.OK);
    }

    @PostMapping("/newSize")
    public @ResponseBody
    ResponseEntity<Sizes> saveNewSizes(Sizes string) {
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
            @RequestPart(value = "image", required = false) List<MultipartFile> multipartFile) {


        productService.saveProductAndEnrollImage(product, multipartFile);


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
