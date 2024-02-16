package com.order.system.domain.service.ports.ouput.message.publisher.payment;


import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
