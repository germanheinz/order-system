package com.order.system.restaurant.service.domain.ports.output.message.publisher;

import com.order.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
