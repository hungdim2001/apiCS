package com.example.apiCS.Controller;


import com.example.apiCS.Dto.request.CartItemRequest;
import com.example.apiCS.Service.CartItemService;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/cartItem")
@RestController
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/postCartItem")
    public ResponseEntity postCartItem(@Valid @RequestBody CartItemRequest cartItemRequest) {
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save cart item successfully", cartItemService.postCartItem(cartItemRequest)));
    }
    @GetMapping("/getCartItem/{userId}")
    public ResponseEntity getCartItemByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get cart Item successfully", cartItemService.getCartItemByUserId(userId)));
    }
}
