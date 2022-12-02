package com.shklyar.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductService {

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


    public Specification<Product> mapProduct(String string) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map;

        map = mapper.readValue(string, Map.class);

        return predicateForProducts(map);
    }

    private Specification<Product> predicateForProducts(Map<String, String> map) {

        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {


                List<Predicate> productPredicateList = new ArrayList<>();

                for (Map.Entry<String, String> entry : map.entrySet()) {

                    productPredicateList.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
                }


                return criteriaBuilder.and(productPredicateList.toArray(new Predicate[0]));
            }
        };
    }

}
