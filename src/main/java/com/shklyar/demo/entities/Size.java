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
@Table(name = "size")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String title;

    @JsonBackReference
    @ManyToMany(mappedBy = "size")
    private Set<Product> products = new HashSet<>();

    public Size(
            String title
    ) {
        this.title = title;
    }


}
