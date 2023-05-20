package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Availability;
import com.shklyar.demo.entities.AvailabilityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvailabilityRepository extends JpaRepository<Availability,Long> , JpaSpecificationExecutor<Availability> {

    public  Availability getByAvailabilityNames(AvailabilityName availabilityName);

}
