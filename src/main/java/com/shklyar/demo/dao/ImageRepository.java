package com.shklyar.demo.dao;


import com.shklyar.demo.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends JpaRepository<Images, Long>, JpaSpecificationExecutor<Images> {

    public Images getByTitle(String title);
}
