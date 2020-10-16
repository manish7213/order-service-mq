package com.manish.orderservice.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Order {

    private String orderId;
    private String name;
    private BigInteger qty;
    private BigDecimal price;

}
