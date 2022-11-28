package com.shklyar.demo.entities;

import javax.persistence.metamodel.SingularAttribute;
import java.math.BigDecimal;

public class Product_ {
    public static volatile SingularAttribute<Product, BigDecimal> price;
    public static volatile SingularAttribute<Product, String> title;
    public static volatile SingularAttribute<Product, String> size;
    public static final String PRICE = "price";
    public static final String TITLE = "title";
    public static final String SIZE = "size";
}
