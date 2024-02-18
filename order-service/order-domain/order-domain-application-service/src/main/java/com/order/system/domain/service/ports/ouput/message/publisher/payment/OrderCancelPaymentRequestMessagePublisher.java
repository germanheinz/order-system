package com.order.system.domain.service.ports.ouput.message.publisher.payment;


import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
