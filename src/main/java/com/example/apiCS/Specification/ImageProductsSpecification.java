package com.example.apiCS.Specification;

import com.example.apiCS.Entity.ImageProducts;
import com.example.apiCS.Entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public final class ImageProductsSpecification {
    public static Specification<ImageProducts> getByProductId(Long id) {
        return (root, query, cb) -> {
            Join<ImageProducts, Product> ImageProductsJoin = root.join("imageProductsList");
            return cb.equal(ImageProductsJoin.get("id"),id);
        };
    }
}
