package com.example.apiCS.Controller;

import com.example.apiCS.Dto.Respone.CategoryResponse;
import com.example.apiCS.Dto.Respone.WeaponResponse;
import com.example.apiCS.Dto.request.CategoryRequest;
import com.example.apiCS.Entity.Category;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.exceptions.DuplicateException;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/api/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/categories")
    public ResponseEntity postCategory(@Valid @RequestBody CategoryRequest category) {
        if (!categoryRepository.existsByName(category.getName())) {
            Category categorySaved = categoryRepository.save(Category.builder().name(category.getName()).build());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObj(HttpStatus.CREATED.value(), true, "create category successfully ", categorySaved));
        }
        throw new DuplicateException(HttpStatus.CONFLICT, "duplicate record");
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> listCategoryResponse = categories.stream().map(item ->
                CategoryResponse.builder().id(item.getId()).name(item.getName()).listWeapon(
                                item.getListWeapon().stream()
                                        .map(weapon -> WeaponResponse.builder()
                                                .id(weapon.getId()).name(weapon.getName()).imageUrl(weapon.getImageUrl()).build()).toList()
                        )
                        .build()).toList();
        return listCategoryResponse;
//        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObj(HttpStatus.FOUND.value(), true, "get categories successfully ", categories));

    }
}
