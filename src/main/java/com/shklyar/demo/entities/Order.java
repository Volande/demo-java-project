package com.shklyar.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "customer_id")
    private Customer order;
    @ManyToOne
    @JoinColumn( name = "product_id")
    private Product product;
    private Double amount;
    private Double price;
    @OneToOne
    @JoinColumn(name = "size_id")
    private Sizes size;

}
