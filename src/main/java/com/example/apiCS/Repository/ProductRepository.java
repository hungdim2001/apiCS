package com.example.apiCS.Repository;

import com.example.apiCS.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    Boolean existsByName(String name);
    @Query(value = "SELECT p FROM Product p join p.category c WHERE c.id =?1 ")
    Page<Product> findByWeaponId(Long Id, Pageable pageable);

//    @Query(value = "SELECT p FROM Product p WHERE p.weaponId =?1 ")
//    Page<Product> findAllAndSort(Long id, Pageable pageable);
//
//    @Query(value = "SELECT p FROM Product p WHERE p.weaponId =?1 ORDER BY ?2 desc ")
//    List<Product> findByWeaponIDPaginationDesc(Long Id, String sort, Pageable pageable);



}

