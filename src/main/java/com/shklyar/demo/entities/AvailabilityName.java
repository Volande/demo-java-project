package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "availability_name")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AvailabilityName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String language;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;
}
