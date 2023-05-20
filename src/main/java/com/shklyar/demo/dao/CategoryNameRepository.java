package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Availability;
import com.shklyar.demo.entities.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryNameRepository extends JpaRepository<CategoryName,Long>, JpaSpecificationExecutor<CategoryName> {
    public CategoryName getByTitle(String string);
}
