package com.example.apiCS.Repository;

import com.example.apiCS.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>, JpaSpecificationExecutor<CartItem> {
    @Transactional
    @Modifying
    @Query(value = "delete from cart_item c where c.user_id = ?1", nativeQuery = true)
    void deleteAllByUserId(Long id);
    @Query(value = "select count(c)>0 from cart_item c  where c.user_id = ?1", nativeQuery = true)
    Boolean existsByUserId(Long id);
//    Boolean findBy
}
