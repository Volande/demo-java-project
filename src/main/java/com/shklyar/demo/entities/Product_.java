package com.shklyar.demo.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import java.util.List;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public class Product_ {
    public static volatile SingularAttribute<Product, List<Category>> category;
    public  final String CATEGORY = "category";
}
