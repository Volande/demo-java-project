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
        this.sizesService=sizesService;
        this.categoryService=categoryService;
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

    public boolean changeOrderImages( Product product) {

        List<Long> listId = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();


        for (int i = 0; i < product.getImage().size(); i++) {
            listId.add(product.getImage().get(i).getId());
            listTitle.add(product.getImage().get(i).getTitle());
        }

        Collections.sort(listId);

        product.setImage(new ArrayList<>());

        for (int i = 0; i < listId.size(); i++) {
            product.getImage().add(new Images(listId.get(i),listTitle.get(i),product));
        }
        productRepository.save(product);

        imageService.deleteUnusedImages();













        /*  productRepository.save(product);

        List<Images> list2 = imageRepository.findImagesByProducts(product);




        List<Images> images = productRepository.findProductById(product.getId()).getImage();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            strings.add(images.get(i).getTitle());
        }


        for (Images images2 : list2) {
            if (!strings.contains(images2.getTitle())) {

                productRepository.findProductById(product.getId()).getImage().remove(images2);
            }
        }


        List<Long> list = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            list.add(images.get(i).getId());
        }

        Long[] list1 = list.toArray(new Long[0]);
        Arrays.sort(list1);

        list = List.of(list1);

        for (int i = 0; i < images.size(); i++) {
            images.get(i).setId(list.get(i));
            images.get(i).setProducts(product);
        }


        imageRepository.saveAll(images);*/


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

        if(product.getImage() != null){
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

    public ResponseEntity<Long> deleteProduct(long productId) {
        Product product = productRepository.findProductById(productId);


        for (int i=0;i<product.getImage().size(); i++){
            imageService.deleteImage(product.getImage().get(i).getTitle());
        }

        productRepository.deleteById(productId);


        return ResponseEntity.ok(product.getId());
    }

}
