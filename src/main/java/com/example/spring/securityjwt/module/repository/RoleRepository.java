package com.example.spring.securityjwt.module.repository;

import com.example.spring.securityjwt.module.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Long> {

    @Query(value = "select r from Role r where r.id=?1")
    Role getRoleById(Long id);

    @Query(value = "select r from  Role r where r.name = ?1")
    Role findByName(String name);
}
