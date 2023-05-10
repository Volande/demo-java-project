package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_information")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String availability;
    private String content;
    private String compound;
    private String language;
    private String title;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    private Product product;
}
