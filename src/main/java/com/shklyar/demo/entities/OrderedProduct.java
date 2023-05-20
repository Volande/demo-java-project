package com.shklyar.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ordered_product")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    @JoinColumn( name = "product_id")
    private Product product;
    private Double amount;
    private Double price;
    @OneToOne
    @JoinColumn(name = "size_id")
    private Size size;

}
