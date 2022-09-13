package com.example.apiCS.Repository;

import com.example.apiCS.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    boolean existsByUserName(String username);


    @Query(value = "SELECT u FROM User u WHERE u.userName =?1 OR u.email = ?1")
    Optional<User> findByUsernameOrEmail(String account);

}
