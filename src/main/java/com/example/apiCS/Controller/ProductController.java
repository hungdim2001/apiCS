package com.example.apiCS.Controller;

import com.example.apiCS.Dto.Respone.ProductResponse;
import com.example.apiCS.Dto.Respone.ProductsResponse;
import com.example.apiCS.Entity.Category;
import com.example.apiCS.Entity.Product;
import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.Repository.ImageProductRepository;
import com.example.apiCS.Repository.ProductRepository;
import com.example.apiCS.commons.Utils.UploadFile;
import com.example.apiCS.exceptions.NotFoundException;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

//package com.example.apiCS.Controller;
//
//import com.example.apiCS.Dto.request.WeaponRequest;
//import com.example.apiCS.Repository.CategoryRepository;
//import com.example.apiCS.Repository.WeaponRepository;
//import com.example.apiCS.Service.WeaponService;
//import com.example.apiCS.helper.ResponseObj;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UploadFile uploadFileService;
    @Autowired
    ImageProductRepository imageProductRepository;


    @RequestMapping(path = "", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postProduct
            (@RequestParam("name")
             String name,
             @RequestParam("categoryName")
             String categoryName,
             @RequestParam("price")
             Float price,
             @RequestParam("priceSale")
             Float priceSale,
             @RequestParam("description")
             String description,
             @RequestParam("image")
             MultipartFile[] image, @RequestParam("thumbnail")
             MultipartFile thumbnail
//             @RequestBody ProductRequest productRequest

            ) {
//        Category category = categoryRepository.findByName(productRequest.getCategoryName()).orElseThrow(() ->
//                new NotFoundException(HttpStatus.NOT_FOUND, "not found category name" + productRequest.getCategoryName()));
//        List<Product> productList = new ArrayList<>();
//        Product productSaved = new Product();
//        productSaved.setName(productRequest.getName());
//        productSaved.setCategory(category);
//        productSaved.setPrice(productRequest.getPrice());
//        productSaved.setPriceSale(productRequest.getPriceSale());
//        productSaved.setDescription(productRequest.getDescription());
//        String thumbnail = uploadFileService.uploadFile(productRequest.getThumbnail(), productRequest.getThumbnail()
//                .getOriginalFilename(), productRequest.getCategoryName());
//        productSaved.setThumbnail(thumbnail);
//        List<ImageProducts> imageProductsList = new ArrayList<>();
//        for (MultipartFile item : productRequest.getImage()) {
//            String image = uploadFileService.uploadFile(item,
//                    item.getOriginalFilename(), productRequest.getCategoryName());
//            ImageProducts imageProductsSave = new ImageProducts();
//            imageProductsSave.setImageUrl(image);
//            imageProductsSave.setProduct(productSaved);
//            imageProductsList.add(imageProductsSave);
//        }
//        productSaved.setImageProductsList(imageProductsList);
//        productList.add(productSaved);
//        category.setListProduct(productList);
//        productRepository.save(productSaved);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "created product successfully", null));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "created product successfully", null));

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity getListProductByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "not found category with id " + categoryId));
        Pageable paging = PageRequest.of(0, 8);

        Page<Product> productList = productRepository.findByWeaponId(categoryId, paging);
        List<ProductResponse> listProductResponse = productList.stream().map((item) ->
                ProductResponse.builder().name(item.getName())
                        .price(item.getPrice())
                        .priceSale(item.getPriceSale())
                        .imageUrl(item.getThumbnail())
                        .build()
        ).collect(Collectors.toList());

        ProductsResponse productsResponse = ProductsResponse.builder()
                .totalPage(productList.getTotalPages())
                .currentPage(1).categoryName(category.getName())
                .products(listProductResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list product successfully", productsResponse));
    }

    @GetMapping("/page/{categoryId}")
    public ResponseEntity getListProductByCategoryIdPagination(@PathVariable Long categoryId, @RequestParam int page) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "not found category with id " + categoryId));
        Pageable paging = PageRequest.of(page - 1, 8);
        Page<Product> productList = productRepository.findByWeaponId(categoryId, paging);
        List<ProductResponse> listProductResponse = productList.stream().map((item) ->
                ProductResponse.builder().name(item.getName())
                        .price(item.getPrice())
                        .priceSale(item.getPriceSale())
                        .imageUrl(item.getThumbnail())
                        .build()
        ).collect(Collectors.toList());

        ProductsResponse productsResponse = ProductsResponse.builder()
                .totalPage(productList.getTotalPages())
                .currentPage(page).categoryName(category.getName())
                .products(listProductResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get list product successfully", productsResponse));

    }

}