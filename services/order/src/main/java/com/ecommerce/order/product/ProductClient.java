package com.ecommerce.order.product;

import com.ecommerce.order.dto.OrderLineDto;
import com.ecommerce.order.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "${application.config.product-url}"
)
public interface ProductClient {
    @PostMapping("/purchase")
    List<ProductResponseDto> checkProductValidity(@RequestBody List<OrderLineDto> products);
}
