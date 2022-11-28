package com.shklyar.demo.entities;





import org.springframework.data.jpa.domain.Specification;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProductFilter
{
    public Specification<Product> titleLike(String title){
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