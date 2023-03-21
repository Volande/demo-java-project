package com.shklyar.demo.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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
    private Long id;
    private String title;
    private Double price;

    private Boolean availability;
    private String content;
    private String compound;

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "products_id")
    private List<Images> image;

    @Size(max = 255)
    private String images;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "produts_size",
            joinColumns = @JoinColumn(name = "products_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Sizes> size = new ArrayList<Sizes>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "produts_categories",
            joinColumns = @JoinColumn(name = "products_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<Category>();

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private Collection collection;


    public void addSizes(Sizes sizes) {
        size.add(sizes);
        sizes.getProducts().add(this);

    }


}
