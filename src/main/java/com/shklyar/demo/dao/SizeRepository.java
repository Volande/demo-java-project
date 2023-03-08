package com.shklyar.demo.dao;


import com.shklyar.demo.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SizeRepository extends JpaRepository<Sizes, Long>, JpaSpecificationExecutor<Sizes> {

    public Sizes getByTitle(String title);





}
