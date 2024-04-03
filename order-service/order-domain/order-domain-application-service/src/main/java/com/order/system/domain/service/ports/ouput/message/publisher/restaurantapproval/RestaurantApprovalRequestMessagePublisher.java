package com.order.system.domain.service.ports.ouput.message.publisher.restaurantapproval;


import com.order.system.domain.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.order.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface RestaurantApprovalRequestMessagePublisher {

    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
