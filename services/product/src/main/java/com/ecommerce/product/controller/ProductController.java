package com.ecommerce.product.controller;

import com.ecommerce.product.dto.*;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid CreateProductDto dto){
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CreateCategoryDto dto){
        return ResponseEntity.ok(productService.createCategory(dto));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return ResponseEntity.ok(productService.getAllCategories());
    }
    @PostMapping("/purchase")
    public ResponseEntity<List<ProductResponseDto>> purchaseProduct(@RequestBody @Valid List<OrderLineDto> dto){

        return ResponseEntity.ok(productService.purchaseProducts(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.getProductById(id));
    }




}
