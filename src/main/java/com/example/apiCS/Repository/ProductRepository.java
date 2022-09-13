package com.example.apiCS.Repository;

import com.example.apiCS.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);

    List<Product> findByWeaponId(Long Id);

    @Query(value = "SELECT p FROM Product p WHERE p.weaponId =?1 ")
    Page<Product> findAllAndSort(Long id, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.weaponId =?1 ORDER BY ?2 desc ")
    List<Product> findByWeaponIDPaginationDesc(Long Id, String sort, Pageable pageable);



}

