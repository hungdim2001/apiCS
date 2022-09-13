package com.example.apiCS.Controller;

import com.example.apiCS.Dto.request.WeaponRequest;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.Repository.WeaponRepository;
import com.example.apiCS.Service.WeaponService;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/weapon")
public class WeaponController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    WeaponRepository weaponRepository;
    @Autowired
    WeaponService weaponService;

    @RequestMapping(path = "/postWeapon", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postWeapon(@ModelAttribute @Valid WeaponRequest weaponRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "created weapon successfully", weaponService.postWeapon(weaponRequest)));
    }

    @GetMapping("/listWeapon")
    public ResponseEntity getListWeapon() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list weapons successfully", weaponService.getAllWeapon()));
    }

    @GetMapping("/listWeapon/{categoryId}")
    public ResponseEntity getListWeaponByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list weapons by category ID successfully", weaponService.getListWeaponByCategoryId(categoryId)));
    }
}
