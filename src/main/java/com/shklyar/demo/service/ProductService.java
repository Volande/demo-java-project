package com.shklyar.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shklyar.demo.dao.CategoryRepository;
import com.shklyar.demo.dao.ImageRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.dao.SizeRepository;
import com.shklyar.demo.entities.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ProductService {
    @Autowired
    public ProductService(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Value("${imageCloudDirectory}")
    private String directory;

    @Value("${image.service.repository}")
    String imagesDirectory;


    @Value("${CloudFrontUrl}")
    private String cloudFrontUrl;


    String urlDelimiter = "/";
    public int minPrice;
    public int maxPrice;
    SizeRepository sizeRepository;
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    ImageRepository imageRepository;

    @Autowired
    SizesService sizesService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CollectionService collectionService;

    ImageService imageService;


    public Product saveProduct(Product product) {

        for (int i = 0; i < product.getCategories().size(); i++) {
            product.getCategories().set(i, categoryService.initCategory(product.getCategories().get(i).getTitle()));
        }

        for (int i = 0; i < product.getSize().size(); i++) {
            product.getSize().set(i, sizesService.initSize(product.getSize().get(i).getTitle()));
        }


        product.setCollection(collectionService.initCollection(product.getCollection().getTitle()));

        if(product.getImage() != null){
            for (int i = 0; i < product.getImage().size(); i++) {
                product.getImage().set(i, imageService.initImages(product.getImage().get(i).getTitle()));
            }
        }


        return productRepository.save(product);
    }

    public boolean saveProductAndEnrollImage(Product product, List<MultipartFile> multipartFile) {
        List<Images> list = new ArrayList<>();
        saveProduct(product);

        if(multipartFile != null){
            for (int i = 0; i < multipartFile.size(); i++) {
                String fileExtension = multipartFile.get(i).getOriginalFilename().substring(multipartFile.get(i).getOriginalFilename().lastIndexOf("."));

                String fileName = product.getId().toString() + "-" + RandomString.make(10) + fileExtension;

                Images image = imageService.addImageToDatabase(cloudFrontUrl + urlDelimiter + directory + fileName, product);


                list.add(image);

                imageService.saveImage(fileName, multipartFile.get(i), fileExtension);

            }

            if(product.getImage() != null){
                for (int i = 0; i < product.getImage().size(); i++) {
                    list.add(product.getImage().get(i));
                }
            }
            product.setImage(list);
            saveProduct(product);
        }



        return true;
    }

    public Sizes enrollSizestoProduct(Product product, Sizes sizes) {

        product.addSizes(sizes);
        return sizeRepository.save(sizes);
    }

    /*public Product saveProductSizes(Product product,Sizes[] sizes){
        saveProduct(product);

        product.setSize((List<Sizes>) sizes);

        return product;
    }*/

   /* public Sizes saveSizes(Sizes sizes){
        return productRepository.save(sizes);
    }*/

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> findProduct(String productParamets) {

        try {

            return productRepository.findAll(mapProduct(productParamets));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public Specification<Product> mapProduct(String string) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map;

        map = mapper.readValue(string, Map.class);

        ObjectMapper mapperCategories = new ObjectMapper();
        Map<String, String> mapCategories;

        return predicateForProducts(map);
    }

    private Specification<Product> predicateForProducts(Map<String, Object> map) {

        return new Specification<Product>() {

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> productPredicateList = new ArrayList<>();

                CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
                Root<Product> category = criteriaQuery.from(Product.class);
                criteriaQuery.select(category);


                if (map.containsKey("minPrice") && map.containsKey(maxPrice)) {
                    minPrice = (Integer) map.get("minPrice");
                    maxPrice = (Integer) map.get("maxPrice");
                    productPredicateList.add(criteriaBuilder.between(root.get("price"), new Double(minPrice), new Double(maxPrice)));
                } else if (map.containsKey("minPrice") && !map.containsKey("maxPrice")) {
                    minPrice = (Integer) map.get("minPrice");
                    productPredicateList.add(criteriaBuilder.greaterThan(root.get("price"), minPrice));
                } else if (!map.containsKey("minPrice") && map.containsKey("maxPrice")) {
                    maxPrice = (Integer) map.get("maxPrice");
                    productPredicateList.add(criteriaBuilder.lessThan(root.get("price"), maxPrice));
                }

                if (map.containsKey("onlyAvailable")) {
                    productPredicateList.add(criteriaBuilder.isTrue(root.get("availability")));
                }

                if (map.containsKey("categories")) {
                    Join<Product, Category> predicateCategory = root.join("categories");

                    ArrayList<String> categories = (ArrayList<String>) map.get("categories");

                    if (categories.size() > 0) {
                        productPredicateList.add(predicateCategory.get("title").in(categories));
                    }
                }

                if (map.containsKey("title")) {
                    productPredicateList.add(criteriaBuilder.equal(root.get("title"), map.get("title")));
                }

                if (map.containsKey("size")) {
                    productPredicateList.add(criteriaBuilder.equal(root.get("size"), map.get("size")));
                }

                if (map.containsKey("collection")) {
                    Join<Product, Collection> predicateCollection = root.join("collection");

                    String collection = (String) map.get("collection");

                    productPredicateList.add(predicateCollection.get("title").in(collection));

                }


                return criteriaBuilder.and(productPredicateList.toArray(new Predicate[0]));
            }


        };

    }


}
