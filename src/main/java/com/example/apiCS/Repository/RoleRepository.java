package com.example.apiCS.Repository;

import com.example.apiCS.Entity.ERole;
import com.example.apiCS.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
   Optional<Role> findByName(ERole name);
}
