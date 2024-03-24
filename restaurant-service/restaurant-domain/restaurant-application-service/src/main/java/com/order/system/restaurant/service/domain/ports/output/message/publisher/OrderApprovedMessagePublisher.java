package com.order.system.restaurant.service.domain.ports.output.message.publisher;

import com.order.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderApprovedMessagePublisher extends DomainEventPublisher<OrderApprovedEvent> {
}
