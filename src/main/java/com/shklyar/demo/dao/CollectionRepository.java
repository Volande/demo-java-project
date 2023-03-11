package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Category;
import com.shklyar.demo.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CollectionRepository extends JpaRepository<Collection, Long>, JpaSpecificationExecutor<Collection> {

    public Collection getByTitle(String title);
}
