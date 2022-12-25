package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String name;
    private String imageUrl;
    private Float price;
    private Float priceSale;
}
