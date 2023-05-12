package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "collection_name")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CollectionName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String language;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;
}
