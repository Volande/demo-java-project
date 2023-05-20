package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Product;
import com.shklyar.demo.entities.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductInformationRepository  extends JpaRepository<ProductInformation, Long>, JpaSpecificationExecutor<ProductInformation> {

    public List<ProductInformation> findProductInformationsByLanguage(String language);
    public ProductInformation getByTitle(String title);
    public ProductInformation getById(int id);

}

