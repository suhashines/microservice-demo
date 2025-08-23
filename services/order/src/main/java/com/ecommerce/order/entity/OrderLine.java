package com.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Integer id;
    private double quantity;
    private Integer productId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
