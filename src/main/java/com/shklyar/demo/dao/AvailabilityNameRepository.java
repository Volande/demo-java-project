package com.shklyar.demo.dao;


import com.shklyar.demo.entities.AvailabilityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvailabilityNameRepository extends JpaRepository<AvailabilityName,Long>, JpaSpecificationExecutor<AvailabilityName> {
    public AvailabilityName getByTitle(String string);
}
