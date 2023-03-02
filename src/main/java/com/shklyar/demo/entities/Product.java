package com.shklyar.demo.entities;

import lombok.*;

import javax.persistence.*;
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
    private  Long id;
    private String title;
    private Double price;

    private Boolean availability;
    private String content;
    private String compound;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "products_id")
    private List<Images> image;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private List<Sizes> size;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable( name = "produts_categories",
            joinColumns = @JoinColumn( name = "products_id"),
            inverseJoinColumns = @JoinColumn( name ="category_id" ))
    private List<Category> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    public void addSizes(Sizes sizes){
        size.add(sizes);

    }
}
