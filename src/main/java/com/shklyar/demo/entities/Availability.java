package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@JsonIgnoreProperties
@Entity
@Table(name = "availability")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "availability",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @OrderBy("id ASC")
    private List<AvailabilityName> availabilityNames;

    @JsonBackReference
    @OneToMany(mappedBy = "availability")
    private Set<Product> products = new HashSet<>();
}
