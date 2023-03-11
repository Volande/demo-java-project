package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collection")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @JsonBackReference
    @OneToMany(mappedBy = "collection")
    private Set<Product> products = new HashSet<>();

    public Collection(String title){
        this.title=title;
    }


}
