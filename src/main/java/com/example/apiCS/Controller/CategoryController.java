package com.example.apiCS.Controller;

import com.example.apiCS.Dto.request.CategoryRequest;
import com.example.apiCS.Entity.Category;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.exceptions.DuplicateException;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/api/category")
@Controller
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
    public ResponseEntity getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObj(HttpStatus.FOUND.value(), true, "get categories successfully ", categories));

    }
}
