package com.ecommerce.product.service;

import com.ecommerce.product.dto.*;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.InsufficientQuantityException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyAccessException;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ProductResponseDto> purchaseProducts(List<OrderLineDto> dto) {

        List<ProductResponseDto> products = new ArrayList<>();

        for(OrderLineDto orderLineDto:dto){
            Product product = productRepository.findById(orderLineDto.productId()).orElseThrow(() -> new ProductNotFoundException("Product not found with id " + orderLineDto.productId()));

            //to-do, checking available amount
            if(product.getQuantity() < orderLineDto.quantity()){
                throw new InsufficientQuantityException("Insufficient quantity for product " + orderLineDto.productId());
            }

            products.add(productMapper.toProductResponseDto(product));
        }

        return products;
    }
}
