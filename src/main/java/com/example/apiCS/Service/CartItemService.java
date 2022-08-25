package com.example.apiCS.Service;

import com.example.apiCS.Dto.Respone.CartItemResponse;
import com.example.apiCS.Dto.Respone.ItemResponse;
import com.example.apiCS.Entity.CartItem;
import com.example.apiCS.Repository.CartItemRepository;
import com.example.apiCS.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiCS.Specification.CartItemSpecification.getByUserId;
@Sẻ
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    public CartItemResponse getCartItemByUserId(Long id) {
        Specification conditions = Specification.where(getByUserId(id));
        List<CartItem> cartItem = cartItemRepository.findAll(conditions);
        if (cartItem.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found user id in cart item");
        }
        List<ItemResponse> dataResponse = cartItem.stream().map(
                (item) -> ItemResponse.builder().product(item.getProduct()).quantity(item.getQuantity()).build()
        ).collect(Collectors.toList());
        CartItemResponse cartItemResponse = CartItemResponse.builder().cartItemId(id).data(dataResponse).build();
        return cartItemResponse;
    }
}
