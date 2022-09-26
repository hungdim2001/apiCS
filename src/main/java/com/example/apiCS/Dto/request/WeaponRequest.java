package com.example.apiCS.Dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WeaponRequest {
    @NotBlank(message = "name is require")
    private String name;
    @NotNull(message = "image is require")
    private MultipartFile image;
    @NotNull(message = "category id is require")
    private Long categoryId;
}
