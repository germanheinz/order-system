package com.order.system.restaurant.service.domain.ports.output.message.publisher;

import com.order.system.outbox.OutboxStatus;
import com.order.system.restaurant.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface RestaurantApprovalResponseMessagePublisher {

    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
