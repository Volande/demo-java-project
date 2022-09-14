package com.shklyar.demo.dao;

import com.shklyar.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
   User findFirstByUsername(String username);
}
