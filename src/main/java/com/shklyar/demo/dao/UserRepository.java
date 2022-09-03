package com.shklyar.demo.dao;


import com.shklyar.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository <User, Long>{
    User findFirstByName(String name);

}
