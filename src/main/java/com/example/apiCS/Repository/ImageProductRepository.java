package com.example.apiCS.Repository;

import com.example.apiCS.Entity.ImageProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageProductRepository extends JpaRepository<ImageProducts, Long> {
    @Query("select i from ImageProducts  i  join i.product  p where p.id = ?1")
    List<ImageProducts> findByProductId(Long id);
}
