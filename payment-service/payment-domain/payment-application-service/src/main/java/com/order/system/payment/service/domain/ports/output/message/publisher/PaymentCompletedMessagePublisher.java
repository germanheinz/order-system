package com.order.system.payment.service.domain.ports.output.message.publisher;

import com.order.system.payment.service.domain.event.PaymentCompletedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
