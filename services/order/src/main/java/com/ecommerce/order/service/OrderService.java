package com.ecommerce.order.service;

import com.ecommerce.order.customer.CustomerClient;
import com.ecommerce.order.dto.*;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.exception.BusinessException;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.kafka.OrderConfirmation;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.payment.PaymentClient;
import com.ecommerce.order.product.ProductClient;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequestDto orderRequestDto) {
        //fetch the customer -> customer-service
        CustomerResponseDto customer = customerClient.getCustomerById(orderRequestDto.customerId());

        HashMap<Integer,Double> orderLineMap = new HashMap<>();

        orderRequestDto.products().forEach(product -> orderLineMap.put(product.productId(), product.quantity()));

        log.info("orderLineMap: {}", orderLineMap);
        //fetch the products -> product-service
        List<ProductResponseDto> products = productClient.checkProductValidity(orderRequestDto.products());

        // inject the quantity of each product

        products.forEach(product -> {
            product.setQuantity(orderLineMap.get(product.getId()));
        });

        log.info("quantity injected in products: {} ",products);

       BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        //calculate totalPrice from products

        for(ProductResponseDto product : products) {
            totalPrice = totalPrice.add(product.getPrice());
        }

        //persist the order
        Order savedOrder = orderRepository.save(orderMapper.toOrder(orderRequestDto,totalPrice));

        // todo start payment process

        PaymentResponseDto paymentResponse = paymentClient.createPayment(
                new PaymentRequestDto(
                        orderRequestDto.paymentMethod(),totalPrice, savedOrder.getId(),customer
                )
        );

        //send the order confirmation

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(savedOrder.getId(),totalPrice,orderRequestDto.paymentMethod(),customer,products)
                //this object must be serialized,the key and value serializer are configured in order-service.yml
        );

        return savedOrder.getId();
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toOrderResponseDtoList(orders);
    }

    public OrderResponseDto getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found")
        );

        log.info("order: {}", order);
        return orderMapper.toOrderResponseDto(order);
    }
}
