package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class WeaponResponse {
    private Long id;
    private String name;
    private String imageUrl;

}
