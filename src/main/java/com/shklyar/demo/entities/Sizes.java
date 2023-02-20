package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "size")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Sizes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @JsonIgnore
    @ManyToOne(optional = false)
    private Product products;


}
