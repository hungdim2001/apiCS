package com.example.apiCS.Controller;

import com.example.apiCS.Entity.Category;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.exception.DuplicateException;
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

    @PostMapping("/postCategory")
    public ResponseEntity postCategory(@Valid @RequestBody Category category) {
        if (!categoryRepository.existsByName(category.getName())) {
            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObj(HttpStatus.CREATED.value(), true, "create category successfully ", category));

        }
            throw new DuplicateException(HttpStatus.CONFLICT, "duplicate record");
    }

    @GetMapping("/getCategories")
    public ResponseEntity getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObj(HttpStatus.FOUND.value(), true, "get categories successfully ", categories));

    }
}
