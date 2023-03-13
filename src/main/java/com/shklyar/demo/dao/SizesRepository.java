package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Category;
import com.shklyar.demo.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SizesRepository extends JpaRepository<Sizes, Long>, JpaSpecificationExecutor<Sizes> {
}
