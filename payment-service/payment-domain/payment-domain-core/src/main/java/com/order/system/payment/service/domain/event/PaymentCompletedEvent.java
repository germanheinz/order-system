package com.order.system.payment.service.domain.event;

import com.order.system.payment.service.domain.entity.Payment;
import com.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {


    public PaymentCompletedEvent(Payment payment,
                                 ZonedDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
    }

}
