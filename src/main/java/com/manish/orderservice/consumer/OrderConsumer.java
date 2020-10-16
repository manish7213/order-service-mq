package com.manish.orderservice.consumer;

import com.manish.orderservice.config.RabbitConfig;
import com.manish.orderservice.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = {RabbitConfig.QUEUE_NAME})
    public void consumeMessageFromQueue(OrderStatus orderStatus) {

        log.info("Message From Queue : {}", orderStatus);
    }
}
