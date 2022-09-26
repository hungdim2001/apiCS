package com.example.apiCS.Service;

import com.example.apiCS.Controller.AuthController;
import com.example.apiCS.Dto.Respone.CartItemResponse;
import com.example.apiCS.Dto.Respone.ItemResponse;
import com.example.apiCS.Dto.request.CartItemRequest;
import com.example.apiCS.Entity.CartItem;
import com.example.apiCS.Entity.Product;
import com.example.apiCS.Entity.User;
import com.example.apiCS.Repository.CartItemRepository;
import com.example.apiCS.Repository.ProductRepository;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.commons.Utils.BeanUtils;
import com.example.apiCS.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiCS.Specification.CartItemSpecification.getByUserId;

@Service
public class CartItemService {
//    @Autowired
//    CartItemRepository cartItemRepository;
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ProductRepository productRepository;
//
//    public CartItemResponse getCartItemByUserId(Long id) {
//        System.out.println(BeanUtils.getBean(AuthController.class));
//        Specification conditions = Specification.where(getByUserId(id));
//        List<CartItem> cartItem = cartItemRepository.findAll(conditions);
//        if (cartItem.isEmpty()) {
//            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found user id in cart item");
//        }
//        List<ItemResponse> dataResponse = cartItem.stream().map(
//                (item) -> ItemResponse.builder().product(item.getProduct()).quantity(item.getQuantity()).build()
//        ).collect(Collectors.toList());
//        CartItemResponse cartItemResponse = CartItemResponse.builder().cartItemId(id).data(dataResponse).build();
//        return cartItemResponse;
//    }

//    public CartItemResponse postCartItem(CartItemRequest cartItemRequest) {
//        User user = userRepository.findById(cartItemRequest.getCartId()).orElseThrow(
//                () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not found user id")
//        );
//        cartItemRepository.deleteAllByUserId(cartItemRequest.getCartId());
//        List<CartItem> listCartItem = cartItemRequest.getData().stream().map(itemRequest -> {
//            Product product = productRepository.findById(itemRequest.getProductId()).orElseThrow(
//                    () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not found product id")
//            );
//            return CartItem.builder()
//                    .quantity(itemRequest.getQuantity())
//                    .product(product)
//                    .user(user).
//                    build();
//        }).toList();
//        user.setCartItems(listCartItem);
//        List<CartItem> listCartItemSave = cartItemRepository.saveAll(listCartItem);
//        List<ItemResponse> dataResponse = listCartItemSave.stream().map(
//                (item) -> ItemResponse.builder().product(item.getProduct()).quantity(item.getQuantity()).build()
//        ).collect(Collectors.toList());
//        CartItemResponse cartItemResponse = CartItemResponse.builder().cartItemId(cartItemRequest.getCartId()).data(dataResponse).build();
//        return cartItemResponse;
//    }
}
