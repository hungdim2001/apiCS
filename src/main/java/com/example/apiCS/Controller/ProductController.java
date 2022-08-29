package com.example.apiCS.Controller;

import com.example.apiCS.Entity.Product;
import com.example.apiCS.Repository.ProductRepository;
import com.example.apiCS.Repository.WeaponRepository;
import com.example.apiCS.exception.DuplicateException;
import com.example.apiCS.exception.NotFoundException;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/product")

@Controller
public class ProductController {
    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WeaponRepository weaponRepository;

    @PostMapping("/postProduct")
    public ResponseEntity postProduct(@Valid @RequestBody Product product) {

        if (!weaponRepository.existsById(product.getWeaponId())) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found weapon id");
        } else if (productRepository.existsByName(product.getName())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "duplicate record");
        }
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObj(HttpStatus.CREATED.value(), true, "create product successfully ", product));

    }

    @GetMapping("/listProduct")
    public ResponseEntity getProduct() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list product successfully ", productRepository.findAll()));
    }

    @GetMapping("/productByID/{productID}")
    public ResponseEntity getProductByID(@PathVariable Long productID) {
        Product product = productRepository.findById(productID).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, " not found product ID"));
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObj(HttpStatus.FOUND.value(), true, "get product successfully", product));
    }

    @GetMapping("/productByWeaponID")
    public ResponseEntity<ResponseObj> getProductByWeaponID(@RequestParam(name = "id") Long id) {
        List<Product> product = productRepository.findByWeaponId(id);
        if (product.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "not found weaponID");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObj(HttpStatus.FOUND.value(), true, "get product by weapon ID successfully", product));
    }

    // get list product by weaponID and pagination
    @GetMapping("/listProductSort")
    public ResponseEntity getProductByWeaponIDAndPagination(@RequestParam(name = "id") Long WeaponId,
                                                            @RequestParam(name = "page") int page,
                                                            @RequestParam(name = "quantity") int quantity,
                                                            @RequestParam(name = "orderBy", defaultValue = "NAME") String orderBy,
                                                            @RequestParam(name = "ASC", defaultValue = "true") boolean ASC) {
        Pageable paging = PageRequest.of(page, quantity, ASC ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);

        List<Product> products = productRepository.findAllAndSort(WeaponId, paging);
        if (products.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "not found weaponID");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list product successfuly", products));

    }

}
