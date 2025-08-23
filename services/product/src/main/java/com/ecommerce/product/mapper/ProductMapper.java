package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.CategoryResponseDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getQuantity(),product.getCategory().getId());
    }

    public CategoryResponseDto toCategoryResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),category.getName(), category.getDescription(), category.getProducts().stream().map(
                this::toProductResponseDto
        ).toList()
        );
    }
}
