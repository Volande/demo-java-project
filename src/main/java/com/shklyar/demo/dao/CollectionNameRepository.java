package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Availability;
import com.shklyar.demo.entities.CollectionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CollectionNameRepository extends JpaRepository<CollectionName,Long>, JpaSpecificationExecutor<CollectionName> {
    public CollectionName getByTitle(String string);
}
