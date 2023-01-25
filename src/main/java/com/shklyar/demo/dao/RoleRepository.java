package com.shklyar.demo.dao;

import com.shklyar.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
   public Role findByAuthority(String authority);
}
