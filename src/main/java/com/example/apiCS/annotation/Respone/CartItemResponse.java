package com.example.apiCS.annotation.Respone;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartItemResponse {
    private long cartItemId;
    private List<ItemResponse> data;
}
