package com.ecommerce.product.service;

import com.ecommerce.product.dto.CategoryResponseDto;
import com.ecommerce.product.dto.CreateCategoryDto;
import com.ecommerce.product.dto.CreateProductDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductResponseDto createProduct(CreateProductDto dto){

        Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .quantity(dto.quantity())
                .category(category)
                .build();
        return productMapper.toProductResponseDto(productRepository.save(product)) ;
    }

    public List<ProductResponseDto> getAllProducts(){

        return productRepository.findAll().stream().map(
                productMapper::toProductResponseDto
        ).toList();
    }

    public List<CategoryResponseDto> getAllCategories(){

        return categoryRepository.findAll().stream().map(
                productMapper::toCategoryResponseDto
        ).toList();
    }

    public Product getProductById(Integer id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Category createCategory(CreateCategoryDto dto){
        Category category = Category.builder().
                name(dto.name()).
                description(dto.description()).
                build();
        return categoryRepository.save(category);
    }
}
