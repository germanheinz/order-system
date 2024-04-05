package com.order.system.payment.service.domain.event;

import com.order.system.payment.service.domain.entity.Payment;
import com.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent {

    public PaymentFailedEvent(Payment payment,
                              ZonedDateTime createdAt,
                              List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }

}
