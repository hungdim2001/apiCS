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

    boolean existsByUsername(String username);


    //    Optional<User> findByEmailOrUsername(String account );
    @Query(value = "SELECT u FROM User u WHERE u.username =?1 OR u.email = ?1")
    Optional<User> findByUsernameOrEmail(String account);

}
