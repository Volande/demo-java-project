package com.shklyar.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @JsonIgnore
    @ManyToOne(optional = true)
    private Product products;

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Images images = (Images) o;

        if (!Objects.equals(id, images.id)) return false;
        return Objects.equals(title, images.title);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
