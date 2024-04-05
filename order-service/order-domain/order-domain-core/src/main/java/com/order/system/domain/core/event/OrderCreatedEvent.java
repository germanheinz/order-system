package com.order.system.domain.core.event;

import com.order.system.domain.core.entity.Order;
import com.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;


public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order,
                             ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
