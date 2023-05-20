package com.shklyar.demo.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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


    private Double quantity = 1.0;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    @OrderBy("id ASC")
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
            targetEntity = Size.class)
    @JoinTable(name = "produts_size",
            joinColumns = @JoinColumn(name = "products_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Size> size = new ArrayList<Size>();


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


    @ManyToOne(cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Collection.class)
    @JoinColumn(name = "collection_id")
    @Fetch(FetchMode.SELECT)
    private Collection collection;
    @ManyToOne(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            },
            targetEntity = Availability.class)
    @JoinColumn(name = "availability_id")
    @Fetch(FetchMode.SELECT)
    private Availability availability;


    public void addSizes(Size sizes) {
        size.add(sizes);
        sizes.getProducts().add(this);

    }

    public void removeImage(Images image){
        this.image.remove(image);
        image.setProducts(null);

    }


}
