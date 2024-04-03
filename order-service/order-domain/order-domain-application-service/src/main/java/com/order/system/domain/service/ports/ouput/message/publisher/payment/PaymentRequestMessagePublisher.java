package com.order.system.domain.service.ports.ouput.message.publisher.payment;



import com.order.system.domain.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.order.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
