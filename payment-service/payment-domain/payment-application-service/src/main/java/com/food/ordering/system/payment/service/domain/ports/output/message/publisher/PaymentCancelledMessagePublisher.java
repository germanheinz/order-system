package com.food.ordering.system.payment.service.domain.ports.output.message.publisher;

import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {
}
