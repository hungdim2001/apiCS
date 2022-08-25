package com.example.apiCS.Specification;


import com.example.apiCS.Entity.CartItem;
import com.example.apiCS.Entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public final class CartItemSpecification {
    public static Specification<CartItem> getByUserId(Long id) {
        return (root, query, cb) -> {
            Join<User, CartItem> userCartItemJoin = root.join("user");
            return cb.equal(userCartItemJoin.get("id"),id);
        };
    }
}
