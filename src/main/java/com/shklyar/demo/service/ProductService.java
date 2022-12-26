package com.shklyar.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Category;
import com.shklyar.demo.entities.Category_;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.Product_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shklyar.demo.entities.Category_.userId;


@Service
public class ProductService {

    private String predicateValuePrice;
    public int minPrice;
    public int maxPrice;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    ProductRepository productRepository;


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

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


    public Specification<Product> mapProduct(String string) throws JsonProcessingException
    {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map;

        map = mapper.readValue(string, Map.class);

        ObjectMapper mapperCategories = new ObjectMapper();
        Map<String, String> mapCategories;

        return predicateForProducts(map);
    }

    private Specification<Product> predicateForProducts(Map<String, Object> map)
    {

        return new Specification<Product>()
        {

            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> productPredicateList = new ArrayList<>();

                CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
                Root<Product> category = criteriaQuery.from(Product.class);
                criteriaQuery.select(category);


                if (map.containsKey("minPrice") && map.containsKey(maxPrice))
                {
                    minPrice = (Integer) map.get("minPrice");
                    maxPrice = (Integer) map.get("maxPrice");
                    productPredicateList.add(criteriaBuilder.between(root.get("price"), new Double(minPrice), new Double(maxPrice)));
                }
                else if (map.containsKey("minPrice") && !map.containsKey("maxPrice"))
                {
                    minPrice = (Integer) map.get("minPrice");
                    productPredicateList.add(criteriaBuilder.greaterThan(root.get("price"), minPrice));
                }
                else if (!map.containsKey("minPrice") && map.containsKey("maxPrice"))
                {
                    maxPrice = (Integer) map.get("maxPrice");
                    productPredicateList.add(criteriaBuilder.lessThan(root.get("price"), maxPrice));
                }

                if (map.containsKey("onlyAvailable"))
                {
                    productPredicateList.add(criteriaBuilder.isTrue(root.get("availability")));
                }

                if (map.containsKey("categories"))

                {
                    Join<Product, Category> predicateCategory = root.join("categories");

                    ArrayList<String> categories = (ArrayList<String>) map.get("categories");

                    if (categories.size() > 0)
                    {
                        productPredicateList.add(predicateCategory.get("title").in(categories));
                    }
                }

/*            for (Map.Entry<String, String> entry : map.entrySet())
            {

               if (entry.getKey().equals("categories"))
               {
                  Join<Product, Category> predicateCategory = category.join("categories");
                  Predicate in = predicateCategory.get("title").in(entry.getValue());
                  productPredicateList.add(in);
                  productPredicateList.remove(entry.getValue());
               }
               else
               {

                  productPredicateList.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));

               }

               productPredicateList.add(criteriaBuilder.between(root.get("price"), minPrice, maxPrice));

            }*/


                return criteriaBuilder.and(productPredicateList.toArray(new Predicate[0]));
            }


        };

    }


}
