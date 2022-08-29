package com.example.apiCS.Service;

import com.example.apiCS.Dto.request.WeaponRequest;
import com.example.apiCS.Entity.Category;
import com.example.apiCS.Entity.Weapon;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.Repository.WeaponRepository;
import com.example.apiCS.commons.Utils.UploadFile;
import com.example.apiCS.exception.DuplicateException;
import com.example.apiCS.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Service
public class WeaponService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    WeaponRepository weaponRepository;
    public Weapon postWeapon(@ModelAttribute @Valid WeaponRequest weaponRequest) {
        String filename = weaponRequest.getName();
        String folder = "weapon";
        Category category = categoryRepository.findById(weaponRequest.getCategoryId()).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "not found category id"));
        if (weaponRepository.existsByName(weaponRequest.getName())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "duplicate weapon");
        }
        Collection<Weapon> weaponList = category.getWeapons();
        String image = UploadFile.uploadFile(weaponRequest.getImage(), filename, folder);
        Weapon weapon = Weapon.builder().
                name(weaponRequest.getName()).
                amount(weaponRequest.getQuantity()).
                categoryId(weaponRequest.getCategoryId()).
                image(image).build();
        weaponList.add(weapon);
        category.setWeapons(weaponList);
        Weapon weaponSave = weaponRepository.saveAndFlush(weapon);
        return weaponSave;
    }
    public List<Weapon> getAllWeapon () {
        return weaponRepository.findAll();
    }
    public List<Weapon> getListWeaponByCategoryId(Long id){
        List<Weapon> listWeapon = weaponRepository.findByCategoryId(id);
        if (listWeapon.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "not found id weapon");
        }
        return listWeapon;

    }


}
