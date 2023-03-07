package com.shklyar.demo.dao;


import com.shklyar.demo.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SizeRepository extends JpaRepository<Sizes, Long>, JpaSpecificationExecutor<Sizes> {

    public Sizes getByTitle(String title);

    public Sizes querySizesById(Long id);

}
