package com.example.apiCS.annotation.Respone;

import com.example.apiCS.Entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {
    private Product product;
    private  int quantity;
}
