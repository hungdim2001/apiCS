package com.example.apiCS.Controller;


import com.example.apiCS.Dto.Respone.CartItemResponse;
import com.example.apiCS.Dto.Respone.ItemResponse;
import com.example.apiCS.Dto.request.CartItemRequest;
import com.example.apiCS.Entity.CartItem;
import com.example.apiCS.Entity.Product;
import com.example.apiCS.Entity.User;
import com.example.apiCS.Repository.CartItemRepository;
import com.example.apiCS.Repository.ProductRepository;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.exception.NotFoundException;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiCS.Specification.CartItemSpecification.getByUserId;

@RequestMapping("/api/cartItem")
@RestController
public class CartItemController {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/postCartItem")
    public ResponseEntity postCartItem(@Valid @RequestBody CartItemRequest cartItemRequest) {
        User user = userRepository.findById(cartItemRequest.getCartId()).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not found user id")
        );
        List<CartItem> listCartItem = new ArrayList<>();
        cartItemRepository.deleteAllByUserId(cartItemRequest.getCartId());
        cartItemRequest.getData().forEach((itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId()).orElseThrow(
                    () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not found product id")
            );
            CartItem cartItem = CartItem.builder()
                    .quantity(itemRequest.getQuantity())
                    .product(product)
                    .user(user).
                    build();
            listCartItem.add(cartItem);
        }));
        user.setCartItems(listCartItem);
        List<CartItem> listCartItemSave = cartItemRepository.saveAll(listCartItem);
        List<ItemResponse> dataResponse = listCartItemSave.stream().map(
                (item) -> ItemResponse.builder().product(item.getProduct()).quantity(item.getQuantity()).build()
        ).collect(Collectors.toList());
        CartItemResponse cartItemResponse = CartItemResponse.builder().cartItemId(cartItemRequest.getCartId()).data(dataResponse).build();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save cart item successfully", cartItemResponse));
    }

    @GetMapping("/getCartItem/{userId}")
    public ResponseEntity getCartItemByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get cart Item successfully", cartItemResponse));

    }
}
