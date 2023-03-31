package com.shklyar.demo.dao;


import com.shklyar.demo.entities.Images;
import com.shklyar.demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images, Long>, JpaSpecificationExecutor<Images> {

    public Images getByTitle(String title);

    public List<Images> findImagesByProducts(Product product);
    public Images findImagesById(Long id);
    public void removeImagesById(Long id);


}
