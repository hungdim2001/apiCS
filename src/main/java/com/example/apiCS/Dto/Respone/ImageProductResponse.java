package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageProductResponse {
    private Long id;
    private String imageUrl;
}
