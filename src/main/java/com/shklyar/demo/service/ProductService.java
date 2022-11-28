package com.shklyar.demo.service;

import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.ProductFilter;
import com.shklyar.demo.entities.Product_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class ProductService
{

   @Autowired
   public ProductService(ProductRepository productRepository)
   {
      this.productRepository = productRepository;
   }

   ProductRepository productRepository;


   public Product saveProduct(Product product){
      return productRepository.save(product);
   }

   public List<Product> findAllProducts(){
      return productRepository.findAll();
   }


   public List<Product> findProduct(String title){
      return productRepository.findAll(titleLike(title));
   }
   private Specification<Product> titleLike(String title){
      return new Specification<Product>() {
         @Override
         public Predicate toPredicate(Root<Product> root,
                                      CriteriaQuery<?> query,
                                      CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.like(root.get(Product_.TITLE), "%"+title+"%");
         }
      };
   }

}
