package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private List<WeaponResponse> listWeapon;
}
