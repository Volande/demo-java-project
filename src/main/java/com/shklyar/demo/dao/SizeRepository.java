package com.shklyar.demo.dao;


import com.shklyar.demo.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SizeRepository extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size> {

    public Size findTopByTitle(String title);

}
