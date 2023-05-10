package com.shklyar.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shklyar.demo.dao.CategoryRepository;
import com.shklyar.demo.dao.ImageRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.dao.SizeRepository;
import com.shklyar.demo.entities.*;
import com.shklyar.demo.entities.Collection;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.FetchType;
import javax.persistence.criteria.*;
import java.util.*;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProductService {
    @Autowired
    public ProductService(ProductRepository productRepository,
                          ImageService imageService,
                          CollectionService collectionService,
                          SizesService sizesService,
                          CategoryService categoryService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.collectionService = collectionService;
        this.sizesService = sizesService;
        this.categoryService = categoryService;
    }

    @Value("${imageCloudDirectory}")
    private String directory;

    @Value("${image.service.repository}")
    String imagesDirectory;


    @Value("${CloudFrontUrl}")
    private String cloudFrontUrl;


    String urlDelimiter = "/";
    public double minPrice;
    public double maxPrice;
    public boolean availability;

    public ArrayList<Object> arrayList = new ArrayList<>();
    SizeRepository sizeRepository;
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    ImageRepository imageRepository;


    SizesService sizesService;

    CategoryService categoryService;

    CollectionService collectionService;

    ImageService imageService;


    public Product saveProduct(Product product) {

        for (int i = 0; i < product.getCategories().size(); i++) {
            product.getCategories().set(i, categoryService.initCategory(product.getCategories().get(i).getTitle()));
        }

        product.setCollection(collectionService.initCollection(product.getCollection().getTitle()));

        for (int i = 0; i < product.getSize().size(); i++) {
            product.getSize().set(i, sizesService.initSize(product.getSize().get(i).getTitle()));
        }


        if (product.getImage() != null) {
            for (int i = 0; i < product.getImage().size(); i++) {
                product.getImage().set(i, imageService.initImages(product.getImage().get(i).getTitle()));
            }
        }


        return productRepository.save(product);
    }

    public boolean changeOrderImages(Product product) {

        List<Long> listId = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();


        for (int i = 0; i < product.getImage().size(); i++) {
            listId.add(product.getImage().get(i).getId());
            listTitle.add(product.getImage().get(i).getTitle());
        }

        Collections.sort(listId);

        product.setImage(new ArrayList<>());

        for (int i = 0; i < listId.size(); i++) {
            product.getImage().add(new Images(listId.get(i), listTitle.get(i), product));
        }
        productRepository.save(product);

        imageService.deleteUnusedImages();

        return true;
    }

    public boolean saveProductAndEnrollImage(Product product, List<MultipartFile> multipartFile) {

        List<Images> list = new ArrayList<>();
        List<Category> categoryList = product.getCategories();
        List<Sizes> sizesList = product.getSize();
        Collection collection = product.getCollection();

        product.setCollection(null);
        product.setSize(new ArrayList<>());
        product.setCategories(new ArrayList<>());

        if (product.getImage() != null) {
            changeOrderImages(product);
        }

        product.setCategories(categoryList);
        product.setCollection(collection);
        product.setSize(sizesList);


        saveProduct(product);

        if (multipartFile != null) {
            for (int i = 0; i < multipartFile.size(); i++) {
                String fileExtension = multipartFile.get(i).getOriginalFilename().substring(multipartFile.get(i).getOriginalFilename().lastIndexOf("."));

                String fileName = product.getId().toString() + "-" + RandomString.make(10) + fileExtension;

                Images image = imageService.addImageToDatabase(cloudFrontUrl + urlDelimiter + directory + fileName, product);


                list.add(image);

                imageService.saveImage(fileName, multipartFile.get(i), fileExtension);

            }

            if (product.getImage() != null) {
                for (int i = 0; i < product.getImage().size(); i++) {
                    list.add(product.getImage().get(i));
                }
            }
            product.setImage(list);
            saveProduct(product);
        }


        return true;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> findProduct(String productParamets) {

        try {

            List<Product> productList = productRepository.findAll(mapProduct(productParamets));

            productList = checkForDuplicates(productList);


            return productList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Product> checkForDuplicates(List<Product> array) {
        Set<Product> set = new HashSet<Product>();

        List<Product> productListNew = new ArrayList<>();
        for (Product e : array) {
            if (!(set.contains(e))) {
                if (e != null) {
                    set.add(e);
                }
            }

        }
        productListNew.addAll(set);

        return productListNew;
    }

    public Specification<Product> mapProduct(String string) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map;

        map = mapper.readValue(string, Map.class);


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


                if (map.get("minPrice") != "" || map.get("maxPrice") != "") {
                    if (map.containsKey("minPrice") && map.containsKey("maxPrice")) {
                        if(map.get("minPrice") != "" ){
                            minPrice = Double.parseDouble((String) map.get("minPrice"));
                        };

                        if(map.get("maxPrice") != ""){
                            maxPrice = Double.parseDouble((String) map.get("maxPrice"));
                        };
                        productPredicateList.add(criteriaBuilder.between(root.get("price"), new Double(minPrice), new Double(maxPrice)));
                    } else if (map.containsKey("minPrice") && !map.containsKey("maxPrice")) {
                        minPrice = Double.parseDouble((String) map.get("minPrice"));
                        productPredicateList.add(criteriaBuilder.greaterThan(root.get("price"), minPrice));
                    } else if (!map.containsKey("minPrice") && map.containsKey("maxPrice")) {
                        maxPrice = Double.parseDouble((String) map.get("maxPrice"));
                        productPredicateList.add(criteriaBuilder.lessThan(root.get("price"), maxPrice));
                    }


                }




                if (map.get("availability") != "" && map.containsKey("availability")) {


                    productPredicateList.add(criteriaBuilder.equal(root.get("availability"), map.get("availability")));

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

                    Join<Product, Sizes> predicateSize = root.join("size");

                    ArrayList<String> size = (ArrayList<String>) map.get("size");

                    if (size.size() > 0) {
                        productPredicateList.add(predicateSize.get("title").in(size));
                    }

                }


                if (map.containsKey("collection")) {
                    Join<Product, Collection> predicateCollection = root.join("collection");
                    ArrayList<String> collections = (ArrayList<String>) map.get("collection");
                    if (collections.size() > 0) {
                        productPredicateList.add(predicateCollection.get("title").in(collections));
                    }

                }


                return  (criteriaBuilder.and(productPredicateList.toArray(new Predicate[0])));
            }


        };

    }

    public ResponseEntity<Long> deleteProduct(long productId) {
        Product product = productRepository.findProductById(productId);


        for (int i = 0; i < product.getImage().size(); i++) {
            imageService.deleteImage(product.getImage().get(i).getTitle());
        }

        productRepository.deleteById(productId);


        return ResponseEntity.ok(product.getId());
    }

}
