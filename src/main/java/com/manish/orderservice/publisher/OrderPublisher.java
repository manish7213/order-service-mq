package com.manish.orderservice.publisher;

import com.manish.orderservice.config.RabbitConfig;
import com.manish.orderservice.model.ORDER_STATUS;
import com.manish.orderservice.model.Order;
import com.manish.orderservice.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/push/")
@RequiredArgsConstructor
@Slf4j
public class OrderPublisher {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/{sellerName}")
    public ResponseEntity placeOrder(@RequestBody Order order, @PathVariable String sellerName) {

       order.setOrderId(UUID.randomUUID().toString());
        //After ordering several other services will be called like restaurant service and payment service.
        //But we should not wait for that , rather we will return Order status to Customer.
        OrderStatus orderStatus = new OrderStatus(order, ORDER_STATUS.PLACED, "Your Order Has Been Places Successfully, It will be delivered from " + sellerName + " seller");
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, orderStatus);
        } catch (Exception e) {
            log.info("Exception Occurred while publish ", e.getMessage());
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(ORDER_STATUS.FULFILLED.name(), HttpStatus.CREATED);
    }
}
