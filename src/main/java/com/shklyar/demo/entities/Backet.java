package com.shklyar.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buckets")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Backet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @OneToOne
    @JoinColumn( name = "user_id")
    private User user;

   @ManyToMany
    @JoinTable( name = "buckets_produts",
        joinColumns = @JoinColumn( name = "buckets_id"),
        inverseJoinColumns = @JoinColumn( name ="product_id" ))
    private List<Product> products;


}
