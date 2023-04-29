package com.shklyar.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn( name = "product_id")
    private Product product;
    private Double amount;
    private Double price;
    @OneToOne
    @JoinColumn(name = "size_id")
    private Sizes size;

}
