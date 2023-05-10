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


    private String availability;

    private Double quantity = 1.0;

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductInformation> productInformation;

    @OneToMany(cascade=CascadeType.ALL)
    @OrderBy("id ASC")
    @JoinColumn(name = "products_id")
    private List<Images> image;

    private Double price;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Sizes.class)
    @JoinTable(name = "produts_size",
            joinColumns = @JoinColumn(name = "products_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Sizes> size = new ArrayList<Sizes>();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Category.class)
    @JoinTable(name = "produts_categories",
            joinColumns = @JoinColumn(name = "products_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<Category>();


    @ManyToOne(fetch = FetchType.EAGER,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Collection.class)
    @JoinColumn(name = "collection_id")
    private Collection collection;


    public void addSizes(Sizes sizes) {
        size.add(sizes);
        sizes.getProducts().add(this);

    }

    public void removeImage(Images image){
        this.image.remove(image);
        image.setProducts(null);

    }


}
