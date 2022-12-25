package com.example.apiCS.Repository;

import com.example.apiCS.Entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    boolean existsByCode(String code);

    @Modifying
    @Transactional
    @Query(value = "delete from Code c where c.idUser = ?1")
    void removeCode(Long idUser);
    @Query(value = "SELECT c FROM Code c WHERE c.code = ?1")
    Optional<Code> findByCode(String code);

}
