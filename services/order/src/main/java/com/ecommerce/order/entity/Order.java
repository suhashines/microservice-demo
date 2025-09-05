package com.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;
    private String customerId;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    //using cascadeType.ALL will create the orderLines object automatically while creating an order
    // by default lazy fetching is used. remember that fetch type eager will stop lazy initialization and fetch all the `N` order lines object under a single order by querying the database N times, creating the classical N+1 query issues
    private List<OrderLine> orderLines ;

}
