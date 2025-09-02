package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderLineDto;
import com.ecommerce.order.dto.OrderRequestDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toOrder(OrderRequestDto orderRequestDto, BigDecimal totalPrice) {
        return Order.builder()
                .customerId(orderRequestDto.customerId())
                .totalPrice(totalPrice)
                .orderLines(orderRequestDto.products().stream().map(
                        orderLineDto -> OrderLine.builder()
                                .productId(orderLineDto.productId())
                                .quantity(orderLineDto.quantity())
                                .build()
                ).collect(Collectors.toList()))
                .paymentMethod(orderRequestDto.paymentMethod())
                .build();
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),order.getCustomerId(),order.getTotalPrice(),order.getPaymentMethod(),order.getOrderLines().stream().map(
                        orderLine -> new OrderLineDto(
                                orderLine.getId(),orderLine.getQuantity(),orderLine.getProductId()
                        )
        ).collect(Collectors.toList())
        ) ;
    }

    public List<OrderResponseDto> toOrderResponseDtoList(List<Order> orders) {
        return orders.stream().map(this::toOrderResponseDto).collect(Collectors.toList());
    }
}
