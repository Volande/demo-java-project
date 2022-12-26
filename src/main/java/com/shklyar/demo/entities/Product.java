package com.shklyar.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String title;
    private Double price;
    private String size;
    private Boolean availability;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable( name = "produts_categories",
            joinColumns = @JoinColumn( name = "products_id"),
            inverseJoinColumns = @JoinColumn( name ="category_id" ))
    private List<Category> categories;
}
