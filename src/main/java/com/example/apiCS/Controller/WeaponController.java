package com.example.apiCS.Controller;

import com.example.apiCS.Entity.Category;
import com.example.apiCS.Entity.Weapon;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.Repository.WeaponRepository;
import com.example.apiCS.exception.DuplicateException;
import com.example.apiCS.exception.NotFoundException;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/api/weapon")
public class WeaponController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    WeaponRepository weaponRepository;


    @PostMapping("/postWeapon")
    public ResponseEntity postWeapon(@Valid @RequestBody Weapon weapon) {
        Category category = categoryRepository.findById(weapon.getCategoryId()).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "not found"));
        if (weaponRepository.existsByName(weapon.getName())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "duplicate weapon");
        }
        Collection<Weapon> weaponList = category.getWeapons();
        weaponList.add(weapon);
        category.setWeapons(weaponList);
//        categoryRepository.saveAndFlush(category); // save
        weaponRepository.saveAndFlush(weapon);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "created weapon successfully", weapon));
    }

    @GetMapping("/listWeapon")
    public ResponseEntity getListWeapon() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list weapons successfully", weaponRepository.findAll()));
    }
    @GetMapping("/listWeapon/{categoryId}")
    public ResponseEntity getListWeaponByCategoryId(@PathVariable Long categoryId) {
        List<Weapon> listWeapon = weaponRepository.findByCategoryId(categoryId);
        if (listWeapon.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list weapons by category ID successfully", listWeapon));
    }
}
